package dev.sunnat629.shutterstockimages.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.Transformations.switchMap
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import dev.sunnat629.shutterstockimages.DSConstants.INITIAL_LOAD_SIZE
import dev.sunnat629.shutterstockimages.DSConstants.PAGE_SIZE
import dev.sunnat629.shutterstockimages.RootApplication
import dev.sunnat629.shutterstockimages.models.datasource.DataSourceFactory
import dev.sunnat629.shutterstockimages.models.datasource.ImageDataSource
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import javax.inject.Inject


class MainViewModel(application: Application) : AndroidViewModel(application) {


    @Inject
    lateinit var scope: CoroutineScope

    @Inject
    lateinit var dataSourceFactory: DataSourceFactory

    var imageSearchList: LiveData<PagedList<ImageContent>>

    private val config = PagedList.Config.Builder()
        .setPageSize(PAGE_SIZE)
        .setInitialLoadSizeHint(INITIAL_LOAD_SIZE)
        .setEnablePlaceholders(false)
        .build()

    init {
        RootApplication.getComponent(application).inject(this)
        imageSearchList = LivePagedListBuilder(dataSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> = switchMap<ImageDataSource, NetworkState>(
        dataSourceFactory.imageContents
    ) { it.networkState }

    fun getInitialLoad(): LiveData<NetworkState> = switchMap<ImageDataSource, NetworkState>(
        dataSourceFactory.imageContents
    ) { it.initialLoad }

    override fun onCleared() {
        super.onCleared()
        scope.cancel()
    }

    fun retry() {
        dataSourceFactory.imageContents.value?.retryAllFailed()
    }

    fun refresh() {
        dataSourceFactory.imageContents.value?.invalidate()
    }
}