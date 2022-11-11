package com.samuel.movie.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.samuel.data.models.Movie
import com.samuel.movie.databinding.MovieItemBinding


class MoviesAdapter(val movieListener: MovieListener) :
    PagingDataAdapter<Movie, MoviesAdapter.MovieViewHolder>(Comparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        return MovieViewHolder(
            MovieItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.setMovie(getItem(position))
    }

    inner class MovieViewHolder(val binding: MovieItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setMovie(movie: Movie?) {
            binding.movie = movie
        }

        init {
            binding.root.setOnClickListener {
                getItem(absoluteAdapterPosition)?.let {
                    movieListener.openMove(it)
                }
            }
        }
    }
}

class Comparator : DiffUtil.ItemCallback<Movie>() {
    override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        // Id is unique.
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
        return oldItem == newItem
    }
}

interface MovieListener {

    fun openMove(movie: Movie)
}

