package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.datasource.DataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton

/**
 * OthersModule.kt
 * This is a module class which provides CoroutineScope and DataSourceFactory during inject.
 *
 * It includes another module -
 * @see RepositoryModule for more details
 * */
@Module(
    includes = [
        RepositoryModule::class]
)
class OthersModule {

    /**
     * This singleton provider provides {@linkplain CoroutineScope scope} which is a context of a scope.
     * */
    @Provides
    @Singleton
    fun provideScope(): CoroutineScope {
        return CoroutineScope(Job() + Dispatchers.Main + Dispatchers.IO)
    }

    /**
     * This singleton provider provides {@linkplain DataSourceFactory} which is responsible for
     * retrieving the data using the DataSource and PagedList configuration.
     *
     * @param scope is the {@linkplain CoroutineScope scope}
     * @param imageRepository is the {@linkplain ImageRepository repository}
     * */
    @Provides
    @Singleton
    fun provideDataSourceFactory(
        scope: CoroutineScope,
        imageRepository: ImageRepository
    ): DataSourceFactory {
        return DataSourceFactory(scope, imageRepository)
    }
}