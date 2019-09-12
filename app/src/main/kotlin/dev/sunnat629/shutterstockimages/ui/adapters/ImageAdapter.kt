package dev.sunnat629.shutterstockimages.ui.adapters

import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import dev.sunnat629.shutterstockimages.R
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import java.util.*

/**
 * ImageAdapter.kt
 * This is a PagedListAdapter which will show in a RecyclerView using two ViewHolders
 * @see ImageAdapter for more details
 * @see NetworkStateViewHolder for more details
 *
 * @param retryCallback is a callback which will trigger if the fetch fails.
 * */
class ImageAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<ImageContent, RecyclerView.ViewHolder>(ImageDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            // This layout will show, if data fetched successfully
            R.layout.list_items -> ImageViewHolder.create(parent)

            // This layout will show, if an error occurs during data fetch
            R.layout.item_network_state -> NetworkStateViewHolder.create(
                parent,
                retryCallback
            )
            else -> throw IllegalArgumentException("unknown view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            R.layout.list_items -> (holder as ImageViewHolder).bindTo(getItem(position))
            R.layout.item_network_state -> (holder as NetworkStateViewHolder).bindTo(networkState)
        }
    }

    private fun hasExtraRow(): Boolean {
        return networkState != null && networkState !== NetworkState.LOADED
    }

    override fun getItemViewType(position: Int): Int {
        return if (hasExtraRow() && position == itemCount - 1) {
            R.layout.item_network_state
        } else {
            R.layout.list_items
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasExtraRow()) 1 else 0
    }

    /**
     * Set the current network state to the adapter but this work only after the initial load
     * and the adapter already have list to add new loading raw to it so the initial loading state
     * the activity responsible for handle it
     *
     * @param newNetworkState the new network state
     */
    fun setNetworkState(newNetworkState: NetworkState) {
        if (!currentList.isNullOrEmpty()) {
            currentList?.size.let {
                val previousState = this.networkState
                val hadExtraRow = hasExtraRow()
                this.networkState = newNetworkState
                val hasExtraRow = hasExtraRow()
                if (hadExtraRow != hasExtraRow) {
                    if (hadExtraRow) {
                        notifyItemRemoved(super.getItemCount())
                    } else {
                        notifyItemInserted(super.getItemCount())
                    }
                } else if (hasExtraRow && previousState !== newNetworkState) {
                    notifyItemChanged(itemCount - 1)
                }
            }
        }
    }

    companion object {

        /**
         * @see ImageDiffCallback is a DiffUtil which is a utility class that can calculate the
         * difference between two lists and output a list of update operations that converts the
         * first list into the second one. * It can be used to calculate updates for a RecyclerView Adapter.
         * */
        private val ImageDiffCallback =
            object : DiffUtil.ItemCallback<ImageContent>() {
                override fun areItemsTheSame(
                    oldItem: ImageContent,
                    newItem: ImageContent
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: ImageContent,
                    newItem: ImageContent
                ): Boolean {
                    return Objects.equals(oldItem, newItem)
                }
            }
    }
}