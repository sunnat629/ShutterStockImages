package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import dev.sunnat629.shutterstockimages.DSConstants.FIRST_PAGE
import dev.sunnat629.shutterstockimages.LoggingTags.DATA_S_FACTORY
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.ERROR
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.LOADED
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.LOADING
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 *
 * This is a dataSource class which is implemented a DataSource using PageKeyedDataSource.
 * if any developer needs to use data from page N - 1 to load page N, then this PageKeyedDataSource uses.
 *
 * @param scope is the {@linkplain CoroutineScope scope}
 * @param imageRepository is the {@linkplain ImageRepository repository}
 * */
class ImageDataSource(
    private val scope: CoroutineScope,
    private val imageRepository: ImageRepository
) : PageKeyedDataSource<Int, ImageContent>() {

    /**
     * _networkState is a MutableLiveData of NetworkState which will changes based on the network status
     * */
    private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()

    /**
     * networkState is a LiveData of NetworkState which will changes based on {@see _networkState}
     * */
    val networkState: LiveData<NetworkState>
        get() = _networkState

    /**
     * _initialLoad is a MutableLiveData of NetworkState which will changes based on the network status
     * */
    private val _initialLoad: MutableLiveData<NetworkState> = MutableLiveData()

    /**
     * initialLoad is a LiveData of NetworkState which will changes based on {@see _initialLoad}
     * */
    val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    /**
     * retry will use to trigger this DataSource class again if there is any error during fetch the
     * data from the server
     * */
    private var retry: (() -> Any)? = null

    /**
     * retryAllFailed is fublic function which will used in viewModel. This is the public version of
     * @see retry
     * */
    fun retryAllFailed() {
        val prevRetry = retry
        retry = null
        prevRetry?.let {
            scope.launch {
                it.invoke()
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageContent>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    /**
     * This function is called first to initialize a PagedList with data.
     * If it's possible to count the items that can be loaded by the DataSource.
     *
     * @param params is LoadInitialParams: Parameters for initial load, including requested load size.
     * @param callback is LoadInitialCallback: Callback that receives initial load data.
     * */
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageContent>
    ) {
        setNetworkState(LOADING)

        scope.launch {
            Timber.tag(DATA_S_FACTORY).d("loadInitial ${params.requestedLoadSize}")
            when (val result = imageRepository.getImages(FIRST_PAGE)) {

                is NetworkResult.Success -> {
                    Timber.tag(DATA_S_FACTORY).d("loadInitial ${FIRST_PAGE + 1}")
                    callback.onResult(result.data.imageContent, null, FIRST_PAGE + 1)
                    setNetworkState(LOADED)
                }

                is NetworkResult.Error -> {
                    retry = {
                        loadInitial(params, callback)
                    }
                    setNetworkState(ERROR(result.exception))
                }

                is NetworkResult.NoInternet -> {
                    retry = {
                        loadInitial(params, callback)
                    }
                    setNetworkState(ERROR(result.message))
                }

                is NetworkResult.RateLimit -> {
                    retry = {
                        loadInitial(params, callback)
                    }
                    setNetworkState(ERROR(result.message))
                }
            }
        }
    }

    /**
     * It's valid to return a different list size than the page size if it's easier,
     * e.g. if your backend defines page sizes.
     * It is generally safer to increase the number loaded than reduce.
     *
     * @param params is LoadParams: Parameters for the load, including the key for the new page, and requested load size.
     * @param callback is LoadCallback: Callback that receives loaded data.
     * */
    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageContent>) {
        scope.launch {
            val key = params.key + 1

            when (val result = imageRepository.getImages(key)) {

                is NetworkResult.Success -> {
                    callback.onResult(result.data.imageContent, key)
                    setNetworkState(LOADED)
                }

                is NetworkResult.Error -> {
                    retry = {
                        loadAfter(params, callback)
                    }
                    setNetworkState(ERROR(result.exception))
                }

                is NetworkResult.NoInternet -> {
                    retry = {
                        loadAfter(params, callback)
                    }
                    setNetworkState(ERROR(result.message))
                }

                is NetworkResult.RateLimit -> {
                    retry = {
                        loadAfter(params, callback)
                    }
                    setNetworkState(ERROR(result.message))
                }
            }
        }
    }

    /**
     * This function sets the network status of
     * @see _networkState value and
     * @see _initialLoad value
     * */
    private fun setNetworkState(networkState: NetworkState) {
        _networkState.postValue(networkState)
        _initialLoad.postValue(networkState)
    }
}