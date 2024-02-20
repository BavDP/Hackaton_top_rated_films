package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieListViewModel @Inject constructor(private var repository: MovieListRepository) :
    ViewModel() {
    val currentPageLiveData = MutableLiveData<Int>(1)
    private val filterValue = MutableLiveData("")
    private val _movieListLiveData = MutableLiveData<List<Movie>>()
    val movieListLiveData: LiveData<List<Movie>> = _movieListLiveData

    var moviesList: Flow<PagingData<Movie>> =
        repository.getMovies(1).cachedIn(viewModelScope)

    fun gotoPage(pageNum: Int) {
        moviesList = repository.getMovies(pageNum).cachedIn(viewModelScope)
        currentPageLiveData.value = pageNum
    }

    fun doFilter(filterValue: String) {
        if (filterValue != this.filterValue.value) {
            this.filterValue.value = filterValue
            gotoPage(1)
        }
    }

    private fun updateCurrentPage() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                repository.fetchTopRatedMovies(currentPageLiveData.value?:1).collect { movies ->
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