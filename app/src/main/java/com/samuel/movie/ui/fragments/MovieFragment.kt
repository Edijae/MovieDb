package com.samuel.movie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.samuel.data.utilities.Result
import com.samuel.movie.databinding.FragmentMovieBinding
import com.samuel.movie.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MovieFragment : Fragment() {
    lateinit var binding: FragmentMovieBinding
    private val args: MovieFragmentArgs by navArgs()
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMovieBinding.inflate(inflater, container, false)
        binding.back.setOnClickListener {
            findNavController().navigateUp()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = args.movieId
        collectStates()
        binding.tryAgain.setOnClickListener { mainViewModel.getMovieDetails(id) }
        mainViewModel.getMovieDetails(id)
    }

    private fun collectStates() {
        lifecycleScope.launch {
            mainViewModel.state.collect {
                when (it) {
                    is Result.Progress -> {
                        binding.setProgress(it.progress)
                        if (it.progress) {
                            binding.infoTv.visibility = View.GONE
                            binding.tryAgain.visibility = View.GONE
                        }
                    }
                    is Result.Failure -> {
                        binding.infoTv.text = it.message
                        binding.infoTv.visibility = View.VISIBLE
                        binding.tryAgain.visibility = View.VISIBLE
                    }
                    is Result.Success -> {
                        binding.movieDetails = it.data
                        binding.infoTv.visibility = View.GONE
                        binding.tryAgain.visibility = View.GONE
                    }
                }
            }
        }
    }

}