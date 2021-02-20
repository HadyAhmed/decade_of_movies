package com.hadi.movies.utils

import android.view.View
import android.widget.ImageView
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import com.bumptech.glide.Glide
import com.hadi.movies.data.model.Photo
import com.hadi.movies.data.model.local.Movie
import com.hadi.movies.ui.details.MoviePhotosAdapter
import com.hadi.movies.ui.list.MoviesAdapter
import com.hadi.movies.utils.AdapterType.HOME_MOVIES
import com.hadi.movies.utils.AdapterType.MOVIE_PHOTOS_ADAPTER

@BindingAdapter("imgSrc")
fun bindImage(imageView: ImageView, photo: Photo?) {
    photo?.let {
        Glide.with(imageView)
            .load("http://farm${photo.farm}.static.flickr.com/${photo.server}/${photo.id}_${photo.secret}.jpg")
            .centerCrop()
            .into(imageView)
    }
}

@Suppress("UNCHECKED_CAST")
@BindingAdapter("recyclerItems", "adapterType")
fun bindRecyclerView(recyclerView: RecyclerView, items: List<Any>?, adapterType: AdapterType) {
    items?.let {
        when (adapterType) {
            MOVIE_PHOTOS_ADAPTER -> {
                val adapter = recyclerView.adapter as MoviePhotosAdapter
                val snapHelper: SnapHelper = PagerSnapHelper()
                snapHelper.attachToRecyclerView(recyclerView)
                adapter.moviePhotos = items as List<Photo>
                adapter.notifyDataSetChanged()
            }
            HOME_MOVIES -> {
                val adapter = recyclerView.adapter as MoviesAdapter
                adapter.submitList(items as List<Movie>)
            }
        }
    }
}

@BindingAdapter("viewVisibility")
fun bindViewVisibility(view: View, showView: Boolean?) {
    view.isVisible = showView == true
}
