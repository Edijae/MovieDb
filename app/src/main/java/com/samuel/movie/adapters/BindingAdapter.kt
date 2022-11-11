package com.samuel.movie.adapters

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.samuel.data.models.Movie
import com.samuel.data.models.MovieDetails
import com.samuel.movie.R


class BindingAdapter {
    companion object {
        val sizes = HashMap<Int, String>()

        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImage(view: ImageView, movie: Movie?) {
            if (movie == null) {
                return
            }
            val size = if (sizes[view.height] == null) {
                val s = movie.getImageSize(view.height)
                sizes[view.height] = s
                s
            } else {
                sizes[view.height]!!
            }
            val url = movie.getImageUrl(size)

            Glide.with(view)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.loading)
                .into(view)
        }

        @BindingAdapter("posterUrl")
        @JvmStatic
        fun setCoverImage(view: ImageView, details: MovieDetails?) {
            details?.let {
                val size = if (sizes[view.height] == null) {
                    val s = it.getImageSize(view.height)
                    sizes[view.height] = s
                    s
                } else {
                    sizes[view.height]!!
                }
                val url = it.getImageUrl(size)

                Glide.with(view)
                    .load(url)
                    .fitCenter()
                    .placeholder(R.drawable.loading)
                    .into(view)
            }
        }

        @BindingAdapter("dropImage")
        @JvmStatic
        fun setDropImage(view: ImageView, details: MovieDetails?) {
            details?.let {
                val size = if (sizes[view.height] == null) {
                    val s = it.getImageSize(view.height)
                    sizes[view.height] = s
                    s
                } else {
                    sizes[view.height]!!
                }
                val url = it.getDropUrl(size)

                Glide.with(view)
                    .load(url)
                    .into(view)
            }

        }
    }
}