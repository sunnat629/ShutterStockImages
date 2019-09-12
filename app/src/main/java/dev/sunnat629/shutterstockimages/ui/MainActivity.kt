package dev.sunnat629.shutterstockimages.ui


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import dev.sunnat629.shutterstockimages.R
import dev.sunnat629.shutterstockimages.RootApplication
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import dev.sunnat629.shutterstockimages.models.networks.Status
import dev.sunnat629.shutterstockimages.ui.adapters.ImageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_network_state.*
import javax.inject.Inject

/**
 * <h1>ShutterStock Images</h1>
 * This is a demo project where a user can see the image in an infinite scrollable view.
 * For this project I have used the "Shutterstock API" for the images.
 *
 * @author  S M Mohi-Us Sunnat
 * @version 1.0
 * @since   8 September 2019
 */
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var imageRepository: ImageRepository

    private lateinit var viewModel: MainViewModel
    private lateinit var imageAdapter: ImageAdapter

    private lateinit var initialLoadObserver: Observer<NetworkState>
    private lateinit var networkStateObserver: Observer<NetworkState>
    private lateinit var imageSearchObserver: Observer<PagedList<ImageContent>>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RootApplication.getComponent(application).inject(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initObservers()
        initAdapter()
        initButtons()
    }

    /**
     * @see initButtons initiate the listeners of the buttons
     * */
    private fun initButtons() {
        retryLoadingButton.setOnClickListener { viewModel.retry() }
    }

    /**
     * @see initObservers initiate all the observers
     * */
    private fun initObservers() {
        initialLoadObserver = Observer {
            setInitialLoadingState(it)
        }

        imageSearchObserver = Observer {
            imageAdapter.submitList(it)
        }

        networkStateObserver = Observer {
            imageAdapter.setNetworkState(it)
        }


        viewModel.getNetworkState().observe(this, networkStateObserver)
        viewModel.getInitialLoad().observe(this, initialLoadObserver)
        viewModel.imageSearchList.observe(this, imageSearchObserver)
    }

    /**
     * @see initAdapter initiate all the recycleView and adapter
     * */
    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        imageAdapter = ImageAdapter {
            viewModel.retry()
        }

        image_recycler_view.layoutManager = linearLayoutManager
        image_recycler_view.adapter = imageAdapter
        image_recycler_view.recycledViewPool
    }

    /**
     * @see setInitialLoadingState initiate the network status on the UI
     * */
    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility =
            if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry UI
        retryLoadingButton.visibility =
            if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE

        loadingProgressBar.visibility =
            if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        // update ui if there is any networkState update after fetching data
        imageAdapter.currentList?.let { imageList ->
            if (imageList.size > 0) {
                retryLoadingButton.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        // Remove all the observers of this app during app kills
        viewModel.getInitialLoad().removeObservers(this)
        viewModel.imageSearchList.removeObservers(this)
        viewModel.getNetworkState().removeObservers(this)
    }
}