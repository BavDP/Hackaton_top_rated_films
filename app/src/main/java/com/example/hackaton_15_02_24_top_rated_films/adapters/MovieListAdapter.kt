package com.example.hackaton_15_02_24_top_rated_films.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hackaton_15_02_24_top_rated_films.R
import com.example.hackaton_15_02_24_top_rated_films.models.Movie

const val BASE_PATH = "https://image.tmdb.org/t/p/w500/"

class MovieListAdapter:
    PagingDataAdapter<Movie, MovieListAdapter.MyViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = getItem(position)
        if (movie!=null) {
            holder.bind(movie)
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val posterMovieIV : ImageView = itemView.findViewById(R.id.posterMovieIV)
        private val titleMovieTV: TextView = itemView.findViewById(R.id.titleMovieTV)
        private val rateMovieTV: TextView = itemView.findViewById(R.id.rateMovieTV)
        private val overviewMovieTV: TextView = itemView.findViewById(R.id.overviewMovieTV)
        fun bind(movie: Movie){
            val title = movie.title
            val rate = movie.rate
            val overview = movie.overview
            Glide.with(itemView)
                .load(BASE_PATH + movie.poster)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(posterMovieIV)
            titleMovieTV.text = title
            rateMovieTV.text = rate.toString()
            overviewMovieTV.text = overview
        }
    }

    class MovieDiffCallBack: DiffUtil.ItemCallback<Movie>(){
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}