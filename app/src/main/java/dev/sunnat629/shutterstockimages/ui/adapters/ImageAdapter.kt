package dev.sunnat629.shutterstockimages.ui.adapters

import androidx.recyclerview.widget.DiffUtil
import dev.sunnat629.shutterstockimages.models.networks.NetworkState
import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import dev.sunnat629.shutterstockimages.R
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import java.util.*


class ImageAdapter(private val retryCallback: () -> Unit) :
    PagedListAdapter<ImageContent, RecyclerView.ViewHolder>(ImageDiffCallback) {

    private var networkState: NetworkState? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.list_items -> ImageViewHolder.create(parent)
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
     * Set the current network state to the adapter
     * but this work only after the initial load
     * and the adapter already have list to add new loading raw to it
     * so the initial loading state the activity responsible for handle it
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

        private val ImageDiffCallback = object : DiffUtil.ItemCallback<ImageContent>() {
            override fun areItemsTheSame(oldItem: ImageContent, newItem: ImageContent): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ImageContent, newItem: ImageContent): Boolean {
                return Objects.equals(oldItem, newItem)
            }
        }
    }
}