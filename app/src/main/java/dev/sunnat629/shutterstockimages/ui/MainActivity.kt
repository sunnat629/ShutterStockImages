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
import timber.log.Timber
import javax.inject.Inject

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
    }

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

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        imageAdapter = ImageAdapter {
            viewModel.retry()
        }

        image_recycler_view.layoutManager = linearLayoutManager
        image_recycler_view.adapter = imageAdapter
        image_recycler_view.recycledViewPool
    }

    private fun setInitialLoadingState(networkState: NetworkState?) {
        //error message
        errorMessageTextView.visibility =
            if (networkState?.message != null) View.VISIBLE else View.GONE
        if (networkState?.message != null) {
            errorMessageTextView.text = networkState.message
        }

        //loading and retry
        retryLoadingButton.visibility =
            if (networkState?.status == Status.FAILED) View.VISIBLE else View.GONE

        loadingProgressBar.visibility =
            if (networkState?.status == Status.RUNNING) View.VISIBLE else View.GONE

        imageAdapter.currentList?.let { imageList ->
            if (imageList.size > 0) {
                retryLoadingButton.visibility = View.GONE
                loadingProgressBar.visibility = View.GONE
                errorMessageTextView.visibility = View.GONE
            }
        }

        retryLoadingButton.setOnClickListener { viewModel.retry() }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.getInitialLoad().removeObservers(this)
        viewModel.imageSearchList.removeObservers(this)
        viewModel.getNetworkState().removeObservers(this)
    }
}