package com.example.hackaton_15_02_24_top_rated_films.adapters

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.hackaton_15_02_24_top_rated_films.api.services.MovieRetrofitService
import com.example.hackaton_15_02_24_top_rated_films.models.Movie
import java.util.Date

class MoviePagingSource(
    private val remoteDataSource: MovieRetrofitService,
    private val fromPageNum: Int = 1
): PagingSource<Int, Movie>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        return try {
            val position = params.key ?: fromPageNum
            val response = remoteDataSource.getTopRatedMovies("uk-UA", position)

            if (response.isSuccessful && response.body()!= null) {
                val movies = response.body()?.results?.map {
                    Movie(id = it.id?:-1,
                        title = it.title?:"",
                        overview = it.overview?:"",
                        poster = it.poster?:"",
                        rate = it.rate?:0.0,
                        voteCount = it.voteCount?:"",
                        releaseDate = it.releaseDate?: Date()
                    )
                }
                if (movies != null) {
                    LoadResult.Page(
                        data = movies,
                        prevKey = if (position == 1) null else (position - 1),
                        nextKey = if (position == response.body()?.totalPages ?: position) null else (position + 1)
                    )
                } else {
                    LoadResult.Error(throw Exception("No Response"))
                }
            } else {
                LoadResult.Error(throw Exception("No Response"))
            }

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }
}