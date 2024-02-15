package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.hackaton_15_02_24_top_rated_films.databinding.FragmentMovieDetailBinding
import com.example.hackaton_15_02_24_top_rated_films.models.MovieDetail

class MovieDetailFragment(movieDetails: MovieDetail) : Fragment() {
    private lateinit var _binding: FragmentMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        return _binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(movieDetails: MovieDetail) = MovieDetailFragment(movieDetails)
    }
}