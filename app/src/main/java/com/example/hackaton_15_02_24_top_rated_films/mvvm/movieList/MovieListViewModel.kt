package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import com.example.hackaton_15_02_24_top_rated_films.models.MovieDetail
import javax.inject.Inject

class MovieListViewModel @Inject constructor(private var repository: MovieListRepository): ViewModel() {
    private var currentPage: Int = -1
    private var filterValue: String = ""
    private val _movieListLiveData = MutableLiveData<List<Movie>>()
    private val _movieDetailLiveData = MutableLiveData<MovieDetail>()
    val movieListLiveData: LiveData<List<Movie>> = _movieListLiveData
    val movieDetailLiveData: LiveData<MovieDetail> = _movieDetailLiveData

    fun gotoPage(pageNum: Int): Unit {
        // TODO: load movies from repository for page @pageNum and set loaded list to movieListLiveData
        currentPage = pageNum
        updateCurrentPage()
    }

    fun updateCurrentPage(): Unit {
        // TODO: load movies for current page when user scrolled to end of that. currentPage - is number of updating page
        if (filterValue.isEmpty()) {
            //TODO: simple load movie list
        } else {
            //TODO: load list using API for search movies by name (implementation filter by name)
        }
    }

    fun doFilter(filterValue: String) {
        if (filterValue != this.filterValue) {
            this.filterValue = filterValue
            currentPage = 1
            updateCurrentPage()
        }
    }

    fun showMovieDetails(movie: Movie): Unit {
        // TODO: this method is called when user tapped some @movie for view its detail information
    }
}