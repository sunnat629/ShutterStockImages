package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import androidx.paging.PageKeyedDataSource
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import android.R.attr.key
import timber.log.Timber


class ImageDataSource(
    private val scope: CoroutineScope,
    private val imageRepository: ImageRepository
) : PageKeyedDataSource<Int, ImageContent>() {

    private val FIRST_PAGE = 1

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, ImageContent>
    ) {
        scope.launch {
            Timber.tag("ASDF").d("loadInitial ${params.requestedLoadSize}")
            when (val result = imageRepository.getImages(FIRST_PAGE)) {
                is NetworkResult.Success -> {
                    Timber.tag("ASDF").d("loadInitial ${FIRST_PAGE + 1}")
                    callback.onResult(result.data.data, null, FIRST_PAGE + 1)
                    networkState.postValue(NetworkState.SUCCEED)
                    initialLoad.postValue(NetworkState.SUCCEED)
                }
                is NetworkResult.Error -> {
                    loadInitial(params, callback)
                    networkState.postValue(NetworkState.ERROR(result.exception))
                    initialLoad.postValue(NetworkState.ERROR(result.exception))
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, ImageContent>) {
        scope.launch {
           val key = params.key + 1
            Timber.tag("ASDF").d("loadAfter ${key}")

            when (val result = imageRepository.getImages(key)) {
                is NetworkResult.Success -> {
                    callback.onResult(result.data.data, key)
                    networkState.postValue(NetworkState.SUCCEED)
                    initialLoad.postValue(NetworkState.SUCCEED)
                }
                is NetworkResult.Error -> {
                    loadAfter(params, callback)
                    networkState.postValue(NetworkState.ERROR(result.exception))
                    initialLoad.postValue(NetworkState.ERROR(result.exception))
                }
            }
        }    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, ImageContent>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var networkState = MutableLiveData<NetworkState>()
    private var initialLoad = MutableLiveData<NetworkState>()

//    fun retry() {
//        scope.launch {
//            when (val result = imageRepository.getImages(1)) {
//                is NetworkResult.Success -> {
//                    networkState.postValue(NetworkState.SUCCEED)
//                    initialLoad.postValue(NetworkState.SUCCEED)
//                }
//                is NetworkResult.Error -> {
//                    networkState.postValue(NetworkState.ERROR(result.exception))
//                    initialLoad.postValue(NetworkState.ERROR(result.exception))
//                }
//            }
//        }
//    }

    fun getNetworkState(): MutableLiveData<NetworkState> {
        return networkState
    }

    fun getInitialLoad(): MutableLiveData<NetworkState> {
        return initialLoad
    }
}