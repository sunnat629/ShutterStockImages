package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import kotlinx.coroutines.CoroutineScope

/**
 * Created by Ahmed Abd-Elmeged on 2/20/2018.
 *
 * A simple data source factory which also provides a way to observe the last created data source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class UsersDataSourceFactory(
    private val scope: CoroutineScope,
    private val imageRepository: ImageRepository
) : DataSource.Factory<Int, ImageSearch>() {

    val usersDataSourceLiveData = MutableLiveData<UsersDataSource>()

    override fun create(): DataSource<Int, ImageSearch> {
        val usersDataSource = UsersDataSource(scope, imageRepository)
        usersDataSourceLiveData.postValue(usersDataSource)
        return usersDataSource
    }

}