package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.hackaton_15_02_24_top_rated_films.R
import com.example.hackaton_15_02_24_top_rated_films.databinding.FragmentMovieListBinding
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListViewModel

class MovieListFragment : Fragment() {
    private lateinit var _binding: FragmentMovieListBinding
    private val viewModel: MovieListViewModel by viewModels<MovieListViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieListBinding.inflate(inflater)
        return _binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() = MovieListFragment()
    }
}