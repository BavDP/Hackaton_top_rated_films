package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.liveData
import com.example.hackaton_15_02_24_top_rated_films.adapters.MoviePagingSource
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import com.example.hackaton_15_02_24_top_rated_films.models.MovieDetail
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(private var repository: MovieListRepository) :
    ViewModel() {
    private var currentPage: Int = -1
    private val filterValue = MutableLiveData("")
    private val _movieListLiveData = MutableLiveData<List<Movie>>()
    private val _movieDetailLiveData = MutableLiveData<MovieDetail>()
    val movieListLiveData: LiveData<List<Movie>> = _movieListLiveData
    val movieDetailLiveData: LiveData<MovieDetail> = _movieDetailLiveData

    val moviesList: LiveData<PagingData<Movie>> =
        repository.getMovies() //.cachedIn(viewModelScope)

    fun gotoPage(pageNum: Int) {
        currentPage = pageNum
        updateCurrentPage()
    }

    fun doFilter(filterValue: String) {
        if (filterValue != this.filterValue.value) {
            this.filterValue.value = filterValue
            currentPage = 1
            filterLoadedList()
        }
    }

    fun showMovieDetails(movie: Movie) {
        _movieDetailLiveData.value = MovieDetail() //TODO create real MovieDetail class
    }

    private fun updateCurrentPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchTopRatedMovies(currentPage).collect { movies ->
                    _movieListLiveData.postValue(movies)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private fun filterLoadedList() {
        if (_movieListLiveData.value.isNullOrEmpty()) return
        viewModelScope.launch(Dispatchers.IO) {
            filterValue.asFlow()
                .debounce(500)
                .collectLatest { filterParam ->
                    _movieListLiveData.value = movieListLiveData.value!!.filter { movie ->
                        true
                    //TODO add function which filter saved list by the filterParam. Exaple movie.name.contains(filterValue, true)
                    }
                }
        }
    }
}