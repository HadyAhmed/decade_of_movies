package com.hadi.movies.ui.list

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.hadi.movies.Constants
import com.hadi.movies.R
import com.hadi.movies.databinding.ActivityItemListBinding
import com.hadi.movies.ui.details.MovieDetailsActivity
import com.hadi.movies.ui.details.MovieDetailsFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private val viewModel: MovieListViewModel by viewModels()

    private var twoPane: Boolean = false
    private lateinit var binding: ActivityItemListBinding
    private lateinit var movieSearchAdapter: MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        twoPane = binding.homeContainerView.movieDetailsHostFragment != null

        viewModel.movieTitle.observe(this) { viewModel.searchForMovie(it) }

        movieSearchAdapter = MoviesAdapter { movieId -> showMovieDetails(movieId) }
        binding.homeContainerView.moviesRv.adapter = movieSearchAdapter

    }

    private fun showMovieDetails(movieId: Int) {
        if (twoPane) {
            supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.movie_details_host_fragment,
                    MovieDetailsFragment::class.java,
                    bundleOf(Constants.MOVIE_ID to movieId), "details"
                ).commit()
        } else {
            val movieDetailsIntent = Intent(this, MovieDetailsActivity::class.java).apply {
                putExtra(Constants.MOVIE_ID, movieId)
            }
            startActivity(movieDetailsIntent)
        }
    }

}