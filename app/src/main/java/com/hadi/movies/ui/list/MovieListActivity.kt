package com.hadi.movies.ui.list

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import androidx.lifecycle.lifecycleScope
import com.hadi.movies.databinding.ActivityItemListBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieListActivity : AppCompatActivity() {

    private val viewModel: MovieListViewModel by viewModels()

    private var twoPane: Boolean = false
    private lateinit var binding: ActivityItemListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityItemListBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        binding.toolbar.title = title

        twoPane = binding.homeContainerView.movieDetailsHostFragment != null

        binding.homeContainerView.searchEt.doOnTextChanged { text, _, _, count ->
            if (text != null && count > 3) {
                searchMovies(text.toString())
            }
        }

    }

    private fun searchMovies(movieTitle: String) {
        lifecycleScope.launch {
            viewModel.searchForMovie(movieTitle).collect {
                binding.homeContainerView.responseTv.text = it.toString()
            }
        }
    }

}