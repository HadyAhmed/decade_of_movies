package com.hadi.movies.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.hadi.movies.data.model.Photo
import com.hadi.movies.databinding.ItemMoviePhotoBinding

class MoviePhotosAdapter : RecyclerView.Adapter<MoviePhotosAdapter.MoviePhotosViewHolder>() {
    var moviePhotos = listOf<Photo>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviePhotosViewHolder =
        MoviePhotosViewHolder(
            ItemMoviePhotoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: MoviePhotosViewHolder, position: Int) {
        holder.bindMoviePhotos(moviePhotos[position])

        if (moviePhotos.size > 1 && position == 0) {
            holder.binding.animationView.playAnimation()
        } else {
            holder.binding.animationView.pauseAnimation()
            holder.binding.animationView.isVisible = false
        }
    }

    override fun getItemCount(): Int = moviePhotos.size

    inner class MoviePhotosViewHolder(val binding: ItemMoviePhotoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal fun bindMoviePhotos(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

}