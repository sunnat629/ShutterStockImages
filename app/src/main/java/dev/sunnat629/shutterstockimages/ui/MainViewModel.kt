package dev.sunnat629.shutterstockimages.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import dev.sunnat629.shutterstockimages.RootApplication
import dev.sunnat629.shutterstockimages.models.datasource.DataSourceFactory
import dev.sunnat629.shutterstockimages.models.datasource.ImageDataSource
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import timber.log.Timber
import javax.inject.Inject


class MainViewModel(application: Application) : AndroidViewModel(application) {

    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var dataSourceFactory: DataSourceFactory

    var imageSearchList: LiveData<PagedList<ImageContent>>
    var imageContent: LiveData<PageKeyedDataSource<Int, ImageContent>>
    init {
        RootApplication.getComponent(application).inject(this)
        imageContent = dataSourceFactory.imageContents
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20 * 2)
            .setEnablePlaceholders(false)
            .build()
        imageSearchList = LivePagedListBuilder(dataSourceFactory, config).build()
    }
    fun refresh(){
        imageContent.value?.invalidate()
    }

//    fun getNetworkState(): LiveData<NetworkState> = Transformations.switchMap<ImageDataSource, NetworkState>(
//        imageSearchList
//    ) { it.getNetworkState() }
//
//    fun getRefreshState(): LiveData<NetworkState> = Transformations.switchMap<ImageDataSource, NetworkState>(
//        dataSourceFactory.usersDSLiveData
//    ) { it.getInitialLoad() }
//
//    fun getInitialLoad(): LiveData<NetworkState> = Transformations.switchMap<ImageDataSource, NetworkState>(
//        dataSourceFactory.usersDSLiveData
//    ) { it.getInitialLoad() }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    fun retry() {
    }
}