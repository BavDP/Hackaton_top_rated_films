package com.example.hackaton_15_02_24_top_rated_films.models

import java.util.Date

class Movie(val id: Int,
            val title: String,
            val overview: String,
            val poster: String,
            val rate: Double,
            val voteCount: String,
            val releaseDate: Date)