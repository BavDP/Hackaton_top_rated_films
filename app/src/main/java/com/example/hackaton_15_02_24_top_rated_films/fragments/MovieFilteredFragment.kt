package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hackaton_15_02_24_top_rated_films.adapters.FilteredMovieAdapter
import com.example.hackaton_15_02_24_top_rated_films.databinding.FragmentMovieFilteredBinding
import com.example.hackaton_15_02_24_top_rated_films.di.DaggerMovieApplicationComponent
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModel
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModelFactory
import javax.inject.Inject

class MovieFilteredFragment: Fragment() {
    private lateinit var _binding: FragmentMovieFilteredBinding
    private lateinit var movieAdapter: FilteredMovieAdapter
    private lateinit var movieList: List<Movie>

    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    private lateinit var viewModel: MovieListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // DI create our viewModel
        val app = DaggerMovieApplicationComponent.create()
        app.inject(this)
        viewModel = ViewModelProvider(this, viewModelFactory)[MovieListViewModel::class.java]
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.movieAdapter = FilteredMovieAdapter()

        movieList = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelableArrayList(KEY_LIST, Movie::class.java) ?: emptyList()
        } else {
            arguments?.getParcelableArrayList(KEY_LIST) ?: emptyList()
        }

        movieAdapter.movies = movieList

        _binding.filteredMovieRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = movieAdapter
        }
        setupListeners()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieFilteredBinding.inflate(inflater)
        return _binding.root
    }

    private fun setupListeners() {
        _binding.btnSearch.setOnClickListener {
            val filteredMovies = viewModel.filterLoadedList(movieList, _binding.etForFiltering.text.toString())
            movieAdapter.movies = filteredMovies
        }
    }

    companion object {
        private const val KEY_LIST = "KEY_LIST"
        @JvmStatic
        fun newInstance(movieList: List<Movie>)= MovieFilteredFragment().apply {
            arguments = bundleOf(KEY_LIST to ArrayList(movieList))
        }
    }
}
