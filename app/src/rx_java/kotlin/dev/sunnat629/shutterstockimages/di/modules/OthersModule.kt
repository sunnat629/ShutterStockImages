package dev.sunnat629.shutterstockimages.di.modules

import dagger.Module
import dagger.Provides
import dev.sunnat629.shutterstockimages.models.api.services.ImageApiServices
import dev.sunnat629.shutterstockimages.models.datasource.DataSourceFactory
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

/**
 * OthersModule.kt
 * This is a module class which provides CompositeDisposable and DataSourceFactory during inject.
 *
 * It includes another module -
 * @see ServiceModule for more details
 * */
@Module(
    includes = [
        ServiceModule::class]
)
class OthersModule {

    /**
     * This singleton provider provides {@linkplain CompositeDisposable} which contains a set of disposables
     * */
    @Provides
    @Singleton
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    /**
     * This singleton provider provides {@linkplain DataSourceFactory} which is responsible for
     * retrieving the data using the DataSource and PagedList configuration.
     *
     * @param compositeDisposable is the {@linkplain CompositeDisposable}
     * @param imageApiServices is the {@linkplain ImageApiServices service}
     * */
    @Provides
    @Singleton
    fun provideDataSourceFactory(
        compositeDisposable: CompositeDisposable,
        imageApiServices: ImageApiServices
    ): DataSourceFactory {
        return DataSourceFactory(compositeDisposable, imageApiServices)
    }
}