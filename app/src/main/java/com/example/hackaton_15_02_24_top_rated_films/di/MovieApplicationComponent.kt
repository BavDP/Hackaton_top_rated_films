package com.example.hackaton_15_02_24_top_rated_films.di

import com.example.hackaton_15_02_24_top_rated_films.api.services.MovieRetrofitService
import com.example.hackaton_15_02_24_top_rated_films.fragments.MovieListFragment
import com.example.hackaton_15_02_24_top_rated_films.mvvm.movieList.MovieListRepository
import dagger.Component
import javax.inject.Singleton

@Component(modules=[NetworkModule::class])
@Singleton
interface MovieApplicationComponent {
    fun movieRetrofit(): MovieRetrofitService
    fun movieRepository(): MovieListRepository
    fun inject(fragment: MovieListFragment)
}