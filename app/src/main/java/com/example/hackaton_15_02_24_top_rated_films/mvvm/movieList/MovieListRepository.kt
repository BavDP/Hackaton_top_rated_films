package com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList

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
                Movie() //TODO map for real Movie class
            }
            emit(movieList ?: emptyList())
        }
    }
}