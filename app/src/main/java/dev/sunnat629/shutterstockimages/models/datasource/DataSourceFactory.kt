package dev.sunnat629.shutterstockimages.models.datasource

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import kotlinx.coroutines.CoroutineScope

/**
 * A simple imageContent source factory which also provides a way to observe the last created imageContent source.
 * This allows us to channel its network request status etc back to the UI. See the Listing creation
 * in the Repository class.
 */
class DataSourceFactory(
    private val scope: CoroutineScope,
    private val imageRepository: ImageRepository
) : DataSource.Factory<Int, ImageContent>() {

    val imageContents = MutableLiveData<ImageDataSource>()

    override fun create(): DataSource<Int, ImageContent> {
        val usersDataSource = ImageDataSource(scope, imageRepository)
        imageContents.postValue(usersDataSource)
        return usersDataSource
    }
}