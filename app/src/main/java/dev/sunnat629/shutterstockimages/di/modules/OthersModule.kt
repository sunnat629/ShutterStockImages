package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.datasource.DataSourceFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import javax.inject.Singleton

@Module(
    includes = [
        RepositoryModule::class]
)
class OthersModule {

    @Provides
    @Singleton
    fun provideScope(): CoroutineScope {
        return CoroutineScope(Job() + Dispatchers.Main + Dispatchers.IO)
    }

    @Provides
    @Singleton
    fun provideDataSourceFactory(
        scope: CoroutineScope,
        imageRepository: ImageRepository
    ): DataSourceFactory {
        return DataSourceFactory(scope, imageRepository)
    }
}