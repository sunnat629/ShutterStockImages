package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.ItemKeyedDataSource
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


class UsersDataSource @Inject constructor(
    private val scope: CoroutineScope,
    private val imageRepository: ImageRepository
) : ItemKeyedDataSource<Int, ImageSearch>() {

    private var networkState = MutableLiveData<NetworkState>()
    private var initialLoad = MutableLiveData<NetworkState>()

    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<ImageSearch>
    ) {
        networkState.postValue(NetworkState.LOADING)
        initialLoad.postValue(NetworkState.LOADING)

        scope.launch {
            when (val result = imageRepository.getImages(1)) {
                is NetworkResult.Success -> {

                    networkState.postValue(NetworkState.SUCCEED)
                    initialLoad.postValue(NetworkState.SUCCEED)
                    Timber.tag("ASDF").d(result.data.toString())
                }
                is NetworkResult.Error -> {
                    loadInitial(params, callback)
                    networkState.postValue(NetworkState.ERROR(result.exception))
                    initialLoad.postValue(NetworkState.ERROR(result.exception))
                    Timber.tag("ASDF").e(result.exception)
                }
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<ImageSearch>) {
        scope.launch {
            when (val result = imageRepository.getImages(params.key)) {
                is NetworkResult.Success -> {

                    networkState.postValue(NetworkState.SUCCEED)
                    initialLoad.postValue(NetworkState.SUCCEED)
                    Timber.tag("ASDF").d(result.data.toString())
                }
                is NetworkResult.Error -> {
                    loadAfter(params, callback)
                    networkState.postValue(NetworkState.ERROR(result.exception))
                    initialLoad.postValue(NetworkState.ERROR(result.exception))
                    Timber.tag("ASDF").e(result.exception)
                }
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<ImageSearch>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getKey(item: ImageSearch): Int {
        return item.page
    }

    fun getNetworkState(): MutableLiveData<NetworkState> {
        return networkState
    }

    fun getInitialLoad(): MutableLiveData<NetworkState> {
        return initialLoad
    }
}