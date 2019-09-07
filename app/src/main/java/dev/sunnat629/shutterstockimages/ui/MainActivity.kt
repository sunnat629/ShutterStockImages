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
import androidx.lifecycle.*
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import dev.sunnat629.shutterstockimages.R
import dev.sunnat629.shutterstockimages.RootApplication
import dev.sunnat629.shutterstockimages.models.api.repositories.ImageRepository
import dev.sunnat629.shutterstockimages.models.api.repositories.UnAuthRepository
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.entities.ImageSearch
import dev.sunnat629.shutterstockimages.models.networks.NetworkResult
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import dev.sunnat629.shutterstockimages.models.networks.Status
import dev.sunnat629.shutterstockimages.ui.adapters.ImageAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_network_state.*
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

    private lateinit var viewModel: MainViewModel
    private lateinit var imageAdapter: ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        RootApplication.getComponent(application).inject(this)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)

        initAdapter()
//        initSwipeToRefresh()
    }

//    private fun initSwipeToRefresh() {
//        viewModel.getRefreshState().observe(this, Observer { networkState ->
//            if (imageAdapter.currentList != null) {
//                if (imageAdapter.currentList!!.size > 0) {
//                    usersSwipeRefreshLayout.isRefreshing =
//                        networkState?.status == NetworkState.LOADING.status
//                } else {
//                    setInitialLoadingState(networkState)
//                }
//            } else {
//                setInitialLoadingState(networkState)
//            }
//        })
//        usersSwipeRefreshLayout.setOnRefreshListener { viewModel.refresh() }
//    }

    private fun initAdapter() {
        val linearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        imageAdapter = ImageAdapter {
            viewModel.retry()
        }
        usersRecyclerView.layoutManager = linearLayoutManager
        usersRecyclerView.adapter = imageAdapter
        usersRecyclerView.recycledViewPool

        viewModel.imageSearchList.observe(this, Observer<PagedList<ImageContent>> {
            imageAdapter.submitList(it)
        })
//        viewModel.getNetworkState()
//            .observe(this, Observer<NetworkState> { imageAdapter.setNetworkState(it) })
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

//        usersSwipeRefreshLayout.isEnabled = networkState?.status == Status.SUCCESS
        retryLoadingButton.setOnClickListener { viewModel.retry() }
    }
}