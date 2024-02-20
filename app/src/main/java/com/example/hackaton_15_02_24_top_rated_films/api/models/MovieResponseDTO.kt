package com.example.hackaton_15_02_24_top_rated_films.api.models

import com.google.gson.annotations.SerializedName

data class MovieResponseDTO(
    @SerializedName("results")
    val results: List<MovieResult>?,
    @SerializedName("page")
    val page: Int?,
    @SerializedName("total_pages")
    val totalPages: Int?,
) {
    data class MovieResult(
        @SerializedName("id")
        val id: Int?,
        @SerializedName("title")
        val title: String?,
        @SerializedName("overview")
        val overview: String?,
        @SerializedName("poster_path")
        val poster: String?,
        @SerializedName("vote_average")
        val rate: Double?
    )
}