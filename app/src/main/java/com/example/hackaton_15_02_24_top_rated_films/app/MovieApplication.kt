package com.example.hackaton_15_02_24_top_rated_films.app

import android.app.Application
import com.example.hackaton_15_02_24_top_rated_films.di.DaggerMovieApplicationComponent

class MovieApplication: Application() {
    val appComponent = DaggerMovieApplicationComponent.create()
}