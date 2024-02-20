package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.example.hackaton_15_02_24_top_rated_films.adapters.MoviePagingSource
import com.example.hackaton_15_02_24_top_rated_films.api.services.MovieRetrofitService
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MovieListRepository @Inject constructor(
    private val movieApiService: MovieRetrofitService
) {
    suspend fun fetchTopRatedMovies(page: Int): Flow<List<Movie>> {
        val response = movieApiService.getTopRatedMovies(page = page)
        if (!response.isSuccessful) throw Exception("Error when fetching data. Code: ${response.errorBody()}")

        return flow {
            val movieList = response.body()?.results?.map { movieResult ->
                Movie(id = movieResult.id?:-1,
                    title = movieResult.title?:"",
                    overview = movieResult.overview?:"",
                    poster = movieResult.poster?:"",
                    rate = movieResult.rate?:0.0)
            }
            emit(movieList ?: emptyList())
        }
    }

    fun getMovies(fromPageNum: Int = 1): Flow<PagingData<Movie>> = Pager(
        config = PagingConfig(pageSize = 10, maxSize = 200),
        pagingSourceFactory = { MoviePagingSource(movieApiService, fromPageNum) }
    ).flow
}