package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import com.example.hackaton_15_02_24_top_rated_films.models.MovieDetail
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class MovieListViewModel @Inject constructor(private var repository: MovieListRepository) :
    ViewModel() {
    val currentPageLiveData = MutableLiveData<Int>(1)
    private val _movieDetailLiveData = MutableLiveData<MovieDetail>()
    val movieDetailLiveData: LiveData<MovieDetail> = _movieDetailLiveData

    var moviesList: Flow<PagingData<Movie>> =
        repository.getMovies(1).cachedIn(viewModelScope)

    fun gotoPage(pageNum: Int) {
        moviesList = repository.getMovies(pageNum).cachedIn(viewModelScope)
        currentPageLiveData.value = pageNum
    }

    /*private fun filterLoadedList(loadedMovieList: List<Movie>, predicate: String): List<Movie> {
        if (loadedMovieList.isNullOrEmpty()) return listOf()
        viewModelScope.launch(Dispatchers.IO) {
            filterValue.asFlow()
                .debounce(500)
                .collectLatest { filterParam ->
                    movieList = movieList.filter { movie ->
                        true
                    //TODO add function which filter saved list by the filterParam. Exaple movie.name.contains(filterValue, true)
                    }
                }
        }
    }*/

    private fun filterLoadedList(loadedMovieList: List<Movie>, predicate: String): List<Movie> {
        if (loadedMovieList.isNullOrEmpty()) return listOf()
        return loadedMovieList.filter { movie ->
            movie.title.contains(predicate)
        }
    }
}