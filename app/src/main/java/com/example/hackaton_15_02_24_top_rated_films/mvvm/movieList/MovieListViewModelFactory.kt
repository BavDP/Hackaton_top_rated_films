package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import javax.inject.Inject

class MovieListViewModelFactory @Inject constructor(private val repository: MovieListRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieListViewModel::class.java)) {
            return MovieListViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}