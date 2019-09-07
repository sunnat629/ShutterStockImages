package dev.sunnat629.shutterstockimages.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import dev.sunnat629.shutterstockimages.R
import dev.sunnat629.shutterstockimages.models.entities.ImageContent
import kotlinx.android.synthetic.main.list_items.view.*


class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bindTo(imageSearch: ImageContent?) {
        Picasso.get()
            .load(imageSearch?.assets?.preview?.url)
            .into(itemView.image)
    }

    companion object {
        fun create(parent: ViewGroup): ImageViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val view = layoutInflater.inflate(R.layout.list_items, parent, false)
            return ImageViewHolder(view)
        }
    }
}