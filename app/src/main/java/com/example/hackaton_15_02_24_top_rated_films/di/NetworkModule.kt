package com.example.hackaton_15_02_24_top_rated_films.di

import com.example.hackaton_15_02_24_top_rated_films.api.services.MovieRetrofitService
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


const val BASE_API = "https://api.themoviedb.org/3/movie/"
const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwOWI4NjYxOTRmZTRiODJhMjJhNWYxMDZmM2I3Y2M4NyIsInN1YiI6IjY1YzgwYzQ2OTQ1MWU3MDE4NDdiNjU5ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.rTI54H7jLKyALGpbgpwVvkTFVkHWUyEBsLMPy4O_pt0"
@Module
class NetworkModule {

    private fun getHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor { chain ->
                var request = chain.request()
                request = request.newBuilder()
                    .addHeader("Authorization", "Bearer $TOKEN")
                    .addHeader("accept", "application/json")
                    .build()
                chain.proceed(request)
            }
            .build()
    }
    @Singleton
    @Provides
    fun provideRetrofitMovieService(): MovieRetrofitService {


        return Retrofit.Builder()
            .baseUrl(BASE_API)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getHttpClient())
            .build()
            .create(MovieRetrofitService::class.java)
    }
}