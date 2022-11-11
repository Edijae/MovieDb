package com.samuel.movie.ui.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.samuel.data.models.Movie
import com.samuel.movie.R
import com.samuel.movie.adapters.MovieListener
import com.samuel.movie.adapters.MoviesAdapter
import com.samuel.movie.databinding.FragmentMainBinding
import com.samuel.movie.utilities.AppUtils
import com.samuel.movie.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainFragment : Fragment(), MovieListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var mainActivity: FragmentActivity
    private val adapter = MoviesAdapter(this)
    private val mainViewModel: MainViewModel by viewModels()
    private var job: Job? = null
    private val listener = object : RecyclerView.AdapterDataObserver() {
        override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
            toggleInfoTv("")
        }

        override fun onItemRangeChanged(positionStart: Int, itemCount: Int) {

        }

        override fun onItemRangeMoved(fromPosition: Int, toPosition: Int, itemCount: Int) {

        }

        override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
            toggleInfoTv(if (adapter.itemCount == 0) getString(R.string.no_movies) else "")
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = requireActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.layoutManager = LinearLayoutManager(mainActivity)
        binding.recyclerView.adapter = adapter
        listenForChanges()
        binding.tryAgain.setOnClickListener {
            binding.infoTv.visibility = View.GONE
            binding.tryAgain.visibility = View.GONE
            getMovies()
        }
        getMovies()
    }

    override fun onResume() {
        super.onResume()
        adapter.registerAdapterDataObserver(listener)
    }

    override fun onPause() {
        super.onPause()
        adapter.unregisterAdapterDataObserver(listener)
    }

    //listen for loadstate to update the UI accordingly
    private fun listenForChanges() {
        lifecycleScope.launch {
            adapter.loadStateFlow
                .distinctUntilChangedBy { it.refresh }.collectLatest {
                    updateProgress(it.refresh)
                }
        }
    }

    private fun updateProgress(loadState: LoadState) {
        when (loadState) {
            is LoadState.Loading -> {
                setLoading(true)
            }
            is LoadState.NotLoading -> {
                setLoading(false)
            }
            is LoadState.Error -> {
                setLoading(false)
                val message =
                    if (AppUtils.hasInternet(mainActivity)) getString(R.string.no_movies) else
                        getString(R.string.failed)
                if (adapter.itemCount == 0) {
                    toggleInfoTv(message)
                } else {
                    Toast.makeText(mainActivity, message, Toast.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun setLoading(loading: Boolean) {
        binding.searchPB.visibility =
            if (loading && adapter.itemCount == 0) View.VISIBLE else View.GONE
    }

    private fun toggleInfoTv(message: String) {
        if (message.isEmpty()) {
            binding.infoTv.visibility = View.GONE
            binding.tryAgain.visibility = View.GONE
        } else {
            binding.infoTv.text = message
            binding.infoTv.visibility = View.VISIBLE
            binding.tryAgain.visibility = View.VISIBLE
        }
    }

    private fun getMovies() {
        if (job != null) {
            job?.cancel()
        }
        job = lifecycleScope.launch {
            setLoading(true)
            mainViewModel.getMovies().collectLatest {
                adapter.submitData(it)
            }
        }
    }

    override fun openMove(movie: Movie) {
        val action = MainFragmentDirections.actionMainFragmentToMovieFragment(movie.id)
        findNavController().navigate(action)
    }
}