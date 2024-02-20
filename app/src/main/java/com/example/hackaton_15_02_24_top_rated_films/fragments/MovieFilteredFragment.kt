package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class MovieFilteredFragment(
    private val list: List<Movie>
) : Fragment() {
    private lateinit var _binding: FragmentMovieFilteredBinding
    private lateinit var movieAdapter: FilteredMovieAdapter

    @Inject
    lateinit var viewModelFactory: MovieListViewModelFactory
    lateinit var viewModel: MovieListViewModel

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

        movieAdapter.movies = list

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
            val filteredMovies = viewModel.filterLoadedList(list, _binding.etForFiltering.text.toString())
            movieAdapter.movies = filteredMovies
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(movieList: List<Movie>) = MovieFilteredFragment(movieList)
    }
}
