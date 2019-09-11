package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import dev.sunnat629.shutterstockimages.DSConstants.FIRST_PAGE
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.ERROR
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.LOADING
import dev.sunnat629.shutterstockimages.models.networks.NetworkState.Companion.LOADED
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber

class ImageDataSource(
    private val scope: CoroutineScope,
    private val imageRepository: ImageRepository
) : PageKeyedDataSource<Int, ImageContent>() {


    private val _networkState: MutableLiveData<NetworkState> = MutableLiveData()
    val networkState: LiveData<NetworkState>
        get() = _networkState

    private val _initialLoad: MutableLiveData<NetworkState> = MutableLiveData()
    val initialLoad: LiveData<NetworkState>
        get() = _initialLoad

    private var retry: (() -> Any)? = null

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

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageContent>
    ) {
        _networkState.postValue(LOADING)
        _initialLoad.postValue(LOADING)

        scope.launch {
            Timber.tag("ASDF").d("loadInitial ${params.requestedLoadSize}")
            when (val result = imageRepository.getImages(FIRST_PAGE)) {

                is NetworkResult.Success -> {
                    Timber.tag("ASDF").d("loadInitial ${FIRST_PAGE + 1}")
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

    private fun setNetworkState(networkState: NetworkState) {
        _networkState.postValue(networkState)
        _initialLoad.postValue(networkState)
    }
}