package dev.sunnat629.shutterstockimages.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations.switchMap
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import dev.sunnat629.shutterstockimages.DSConstants.INITIAL_LOAD_SIZE
import dev.sunnat629.shutterstockimages.DSConstants.PAGE_SIZE
import dev.sunnat629.shutterstockimages.models.datasource.DataSourceFactory
import dev.sunnat629.shutterstockimages.models.datasource.ImageDataSource
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.cancel
import javax.inject.Inject

/**
 * MainViewModel.kt
 * This is the view model class of the main activity. It will contains all the data of the actions
 * and provide to the activity.
 *
 * @param dataSourceFactory - This mutable variable is {@code @Injected} and responsible for
 * retrieving the data using the DataSource and PagedList configuration.
 * */
class MainViewModel @Inject constructor(
    private val dataSourceFactory: DataSourceFactory
) : ViewModel()
{

    /**
     * This mutable variable defines a scope for new coroutines. Every coroutines builder is an extension on
     * CoroutineScope and inherits its coroutineContext to automatically propagate both context
     * elements and cancellation.
     * */
    private val scope = CoroutineScope(Job() + Dispatchers.Main + Dispatchers.IO)


    /**
     * This immutable variable contains the image data after fetching from.
     * */
    var imageSearchList: LiveData<PagedList<ImageContent>>


    /**
     * This immutable variable contains the PagedList.Config where we mention the loading page size and an
     * initial loading size.
     * */
    private val config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
        .setEnablePlaceholders(false)
        .build()


    /**
     * initialize the viewModel where we inject the component and retrieve the image data using
     * DataSourceFactory.
     * */
    init {
        imageSearchList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    /**
     * This function is a liveData of the NetworkState which observe the network status of
     * this project.
     * */
    fun getNetworkState(): LiveData<NetworkState> = switchMap<ImageDataSource, NetworkState>(
        dataSourceFactory.imageContents
    ) { it.networkState }

    /**
     * This function is a liveData of the NetworkState which observe the network status of
     * this project.
     * */
    fun getInitialLoad(): LiveData<NetworkState> = switchMap<ImageDataSource, NetworkState>(
        dataSourceFactory.imageContents
    ) { it.initialLoad }

    /**
     * This function will use if the fetched functionality failed somehow in the initial stage or
     * middle of the task.
     * */
    fun retry() {
        dataSourceFactory.imageContents.value?.retryAllFailed()
    }

    /**
     * This function will used for reset the list and fetch the fresh data from the server.
     * */
    fun refresh() {
        dataSourceFactory.imageContents.value?.invalidate()
    }

    /**
     * This override function kills the lifecycle of this viewModel and also cancel the scope.
     * */
    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }
}