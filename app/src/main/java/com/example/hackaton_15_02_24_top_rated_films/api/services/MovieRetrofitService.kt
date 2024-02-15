package com.example.hackaton_15_02_24_top_rated_films.api.services

import com.example.hackaton_15_02_24_top_rated_films.api.models.MovieResponseDTO
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieRetrofitService {
    @GET("top_rated")
    fun getTopRated(@Query("page")page: Int): List<MovieResponseDTO>
}