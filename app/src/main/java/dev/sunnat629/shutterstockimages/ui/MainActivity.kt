package dev.sunnat629.shutterstockimages.ui

/**
 * <h1>ShutterStock Images</h1>
 * This is a demo project where a user can see the image in an infinite scrollable view.
 * For this project I have used the "Shutterstock API" for the images.
 *
 * @author  S M Mohi-Us Sunnat
 * @version 1.0
 * @since   8 September 2019
 */
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.sunnat629.shutterstockimages.R
import dev.sunnat629.shutterstockimages.RootApplication
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.api.repositories.UnAuthRepository
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var imageRepository: ImageRepository

    @Inject
    lateinit var unAuthRepository: UnAuthRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RootApplication.getComponent(application).inject(this)

        GlobalScope.launch(Dispatchers.Main) {
            when (val result = imageRepository.getImages()) {
                is NetworkResult.Success -> {
                    Timber.tag("ASDF").d(result.data.toString())
                }
                is NetworkResult.Error -> {
                    Timber.tag("ASDF").e(result.exception)
                }
            }
        }
    }
}