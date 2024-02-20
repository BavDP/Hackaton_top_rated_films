package com.example.hackaton_15_02_24_top_rated_films.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hackaton_15_02_24_top_rated_films.adapters.BASE_PATH
import com.example.hackaton_15_02_24_top_rated_films.databinding.FragmentMovieDetailBinding
import com.example.hackaton_15_02_24_top_rated_films.models.Movie

class MovieDetailFragment(private val movieDetails: Movie) : Fragment() {
    private lateinit var _binding: FragmentMovieDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieDetailBinding.inflate(inflater)
        setDetailMovie()
        return _binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(movieDetails: Movie) = MovieDetailFragment(movieDetails)
    }
    fun setDetailMovie(){
        val data: String = _binding.dataMovieDetailTV.text.toString() + movieDetails.releaseDate.toString()
        val voteCount: String = _binding.numberVotesDetailTV.text.toString() + movieDetails.voteCount
        val rate = _binding.rateMovieDetailTV.text.toString() + movieDetails.rate.toString()
        val textOverview ="\t" + _binding.overviewMovieDetailTV.text.toString() + movieDetails.overview

        Glide.with(requireContext())
            .load(BASE_PATH + movieDetails.poster)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
            .into(_binding.posterDetailIV)
        _binding.toolbarDetailMovie.title = movieDetails.title
        _binding.dataMovieDetailTV.text = data
        _binding.numberVotesDetailTV.text = voteCount
        _binding.rateMovieDetailTV.text = rate
        _binding.overviewMovieDetailTV.text = textOverview

    }
}