package com.moe.a500pixels.popular.ui


import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.moe.a500pixels.databinding.ListItemPhotosBinding
import com.moe.a500pixels.popular.data.Photo
import kotlinx.android.synthetic.main.layout_photo_header.view.*


/**
 * Adapter for the [RecyclerView] in [PhotosFragment].
 */
class PhotosAdapter : PagedListAdapter<Photo, PhotosAdapter.ViewHolder>(PhotoSettDiffCallback()) {

    private lateinit var recyclerView: RecyclerView

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var photo = getItem(position)
        photo?.let {
            holder.apply {
                bind(createOnClickListener(photo), photo, isGridLayoutManager())
                itemView.tag = photo

                holder.binding.layoutPhoto.title.text = photo.name
                holder.binding.layoutPhoto.author.text = photo.user.fullname
                holder.binding.layoutPhoto.text_comment.text = photo.votes_count.toString()
                holder.binding.layoutPhoto.text_like.text = photo.comments_count.toString()

                Glide.with(holder.itemView.context).load(photo.user.userpic_url)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .apply(RequestOptions.circleCropTransform())
                    .into(holder.binding.layoutPhoto.user_picture)


                Glide.with(holder.itemView.context).load(photo.image_url[0])
                    .fitCenter()
                    .override(Target.SIZE_ORIGINAL, holder.binding.layoutPhoto.image.height)
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                    .listener(object : RequestListener<Drawable> {
                        override fun onLoadFailed(
                            e: GlideException?, model: Any?,
                            target: Target<Drawable>?, isFirstResource: Boolean
                        ): Boolean = false

                        override fun onResourceReady(
                            resource: Drawable?,
                            model: Any?,
                            target: Target<Drawable>?,
                            dataSource: DataSource?,
                            isFirstResource: Boolean
                        ): Boolean {
                            holder.binding.layoutPhoto.image.animate().alpha(1F).setDuration(250).start()
                            return false
                        }
                    })
                    .into(holder.binding.layoutPhoto.image)

            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ListItemPhotosBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.recyclerView = recyclerView
    }

    private fun createOnClickListener(photo: Photo): View.OnClickListener {
        return View.OnClickListener {
            val direction = PhotosFragmentDirections.actionToPhotoDetailFragment(photo)
            it.findNavController().navigate(direction)
        }
    }


    private fun isGridLayoutManager() = recyclerView.layoutManager is GridLayoutManager

    class ViewHolder(internal val binding: ListItemPhotosBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(
            listener: View.OnClickListener,
            item: Photo,
            isGridLayoutManager: Boolean
        ) {
            binding.apply {
                clickListener = listener
                photo = item
                if (isGridLayoutManager) {
                    layoutPhoto.header.visibility = View.GONE
                    layoutPhoto.footer.visibility = View.GONE
                } else {
                    layoutPhoto.header.visibility = View.VISIBLE
                    layoutPhoto.footer.visibility = View.VISIBLE
                }
                executePendingBindings()
            }
        }
    }
}

private class PhotoSettDiffCallback : DiffUtil.ItemCallback<Photo>() {

    override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
        return oldItem == newItem
    }
}