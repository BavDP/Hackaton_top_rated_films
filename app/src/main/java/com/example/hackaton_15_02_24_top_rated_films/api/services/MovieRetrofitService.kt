package com.example.hackaton_15_02_24_top_rated_films.api.services

import com.example.hackaton_15_02_24_top_rated_films.api.models.MovieResponseDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRetrofitService {
    @GET("top_rated")
    suspend fun getTopRatedMovies(
        @Query("language") language: String = "uk-UA",
        @Query("page") page: Int
    ): Response<MovieResponseDTO>
}