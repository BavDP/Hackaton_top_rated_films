package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.hackaton_15_02_24_top_rated_films.R
import com.example.hackaton_15_02_24_top_rated_films.adapters.MovieListAdapter
import com.example.hackaton_15_02_24_top_rated_films.databinding.FragmentMovieListBinding
import com.example.hackaton_15_02_24_top_rated_films.di.DaggerMovieApplicationComponent
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModel
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModelFactory
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListFragment : Fragment() {
    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    lateinit var viewModel: MovieListViewModel

    private lateinit var _binding: FragmentMovieListBinding
    private var loadMovieListDef: Deferred<Unit>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DI create our viewModel
        val app = DaggerMovieApplicationComponent.create()
        app.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding.movieRV.layoutManager = LinearLayoutManager(requireContext())
        _binding.movieRV.adapter = MovieListAdapter()
        setupListeners()
        setupObservers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return _binding.root
    }

    private fun reloadMovieList(fromPage: Int) {
        loadMovieListDef?.cancel()
        loadMovieListDef = viewLifecycleOwner.lifecycleScope.async {
            viewModel.moviesList.collectLatest { pagingData ->
                (_binding.movieRV.adapter as MovieListAdapter).submitData(pagingData)
            }
        }
        loadMovieListDef?.start()
    }

    private fun setupObservers() {

        viewModel.currentPageLiveData.observe(viewLifecycleOwner) {page ->
            reloadMovieList(page)
        }

    }

    private fun setupListeners() {
        _binding.gotoPageBtn.setOnClickListener {
            val pageNum = _binding.pageNumEditText.text.toString()
            if (pageNum.toIntOrNull() != null) {
                viewModel.gotoPage(pageNum.toInt())
            }
        }
    }

    private fun getLoadedMovies(): List<Movie> {
        return (_binding.movieRV.adapter as MovieListAdapter).snapshot().items
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }
}