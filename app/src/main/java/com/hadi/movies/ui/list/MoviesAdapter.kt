package com.hadi.movies.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.hadi.movies.data.model.local.Movie
import com.hadi.movies.databinding.ItemMovieBinding

class MoviesAdapter(private val sendId: (movieId: Int) -> Unit) :
    ListAdapter<Movie, MoviesAdapter.MovieViewHolder>(
        object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean =
                oldItem == newItem
        }
    ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder =
        MovieViewHolder(
            ItemMovieBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        getItem(position)?.let { holder.bindMovie(it) }
    }

    inner class MovieViewHolder(private val binding: ItemMovieBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bindMovie(movie: Movie) {
            binding.movieItem = movie
            binding.movieLayout.setOnClickListener { sendId.invoke(movie.id) }
            binding.executePendingBindings()
        }
    }

}