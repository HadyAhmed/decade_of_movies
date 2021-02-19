package com.hadi.movies.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.hadi.movies.R
import com.hadi.movies.ui.list.MovieListActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private val splashViewModel by viewModels<SplashViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        splashViewModel.startApplication.observe(this) {
            if (it == true) {
                startActivity(Intent(this, MovieListActivity::class.java))
                finish()
            }
        }
    }
}