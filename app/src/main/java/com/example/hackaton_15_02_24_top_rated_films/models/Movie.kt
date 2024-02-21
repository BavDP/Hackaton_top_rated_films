package com.example.hackaton_15_02_24_top_rated_films.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
class Movie(val id: Int,
            val title: String,
            val overview: String,
            val poster: String,
            val rate: Double,
            val voteCount: String,
            val releaseDate: Date): Parcelable