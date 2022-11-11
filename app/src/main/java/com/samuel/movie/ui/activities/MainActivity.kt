package com.samuel.movie.ui.activities

import AppIdlingResource
import android.os.Bundle
import androidx.activity.viewModels
import androidx.annotation.Nullable
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.test.espresso.IdlingResource
import com.samuel.movie.databinding.ActivityMainBinding
import com.samuel.movie.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    // The Idling Resource for testing purposes only with expresso.
    // visible only in development
    @Nullable
    private var mIdlingResource: AppIdlingResource? = null


    private val mainViewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    /**
     * Only called from test, creates and returns a new [AppIdlingResource].
     */
    @VisibleForTesting
    fun getIdlingResource(): IdlingResource? {
        if (mIdlingResource == null) {
            mIdlingResource = AppIdlingResource()
        }
        return mIdlingResource
    }
}

