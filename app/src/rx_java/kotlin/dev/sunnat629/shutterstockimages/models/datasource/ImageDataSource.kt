package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import dev.sunnat629.shutterstockimages.DSConstants.FIRST_PAGE
import dev.sunnat629.shutterstockimages.LoggingTags.DATA_SOURCE
import dev.sunnat629.shutterstockimages.LoggingTags.DATA_S_FACTORY
import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.ERROR
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.LOADED
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.LOADING
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

/**
 *
 * This is a dataSource class which is implemented a DataSource using PageKeyedDataSource.
 * if any developer needs to use data from page N - 1 to load page N, then this PageKeyedDataSource uses.
 *
 * @param compositeDisposable is the {@linkplain CompositeDisposable}
 * @param imageApiServices is the {@linkplain ImageApiServices service}
 * */
class ImageDataSource(
    private val compositeDisposable: CompositeDisposable,
    private val imageApiServices: ImageApiServices
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
    private var retry: Completable? = null

    /**
     * retryAllFailed is public function which will used in viewModel. This is the public version of
     * @see retry
     * */
    fun retryAllFailed() {

        val prevRetry = retry
        retry = null
        prevRetry?.let {
            compositeDisposable.add(
                it
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ }, { throwable -> Timber.tag(DATA_SOURCE).e(throwable) })
            )
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
        Timber.tag(DATA_S_FACTORY).d("loadInitial ${params.requestedLoadSize}")
        setInitNetworkState(LOADING)

        compositeDisposable.add(
            imageApiServices.getImages(FIRST_PAGE)
                .subscribe(
                    { images ->
                        setInitNetworkState(LOADED)
                        callback.onResult(images.imageContent, null, FIRST_PAGE)
                    },
                    { throwable ->
                        retry = Completable.fromAction { loadInitial(params, callback) }
                        setInitNetworkState(ERROR(throwable.message))
                    }
                )
        )
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

        val key = params.key + 1
        Timber.tag(DATA_S_FACTORY).d("loadAfter $key")

        compositeDisposable.add(
            imageApiServices.getImages(key)
                .subscribe(
                    { image ->
                        _networkState.postValue(LOADED)
                        callback.onResult(image.imageContent, key)
                    },
                    { throwable ->
                        retry = Completable.fromAction { loadAfter(params, callback) }
                        _networkState.postValue(ERROR(throwable.message))
                    }
                )
        )
    }

    /**
     * This function sets the network status of
     * @see _networkState value and
     * @see _initialLoad value
     * */
    private fun setInitNetworkState(networkState: NetworkState) {
        _networkState.postValue(networkState)
        _initialLoad.postValue(networkState)
    }
}