package com.example.hackaton_15_02_24_top_rated_films.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hackaton_15_02_24_top_rated_films.R
import com.example.hackaton_15_02_24_top_rated_films.models.Movie

class MovieListAdapter(private val movieList: List<Movie>):
    ListAdapter<Movie, MovieListAdapter.MyViewHolder>(MovieDiffCallBack()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.movie_layout, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val movie = movieList[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int {
        return movieList.size
    }
    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val posterMovieIV : ImageView = itemView.findViewById(R.id.posterMovieIV)
        private val titleMovieTV: TextView = itemView.findViewById(R.id.titleMovieTV)
        private val rateMovieTV: TextView = itemView.findViewById(R.id.rateMovieTV)
        private val overviewMovieTV: TextView = itemView.findViewById(R.id.overviewMovieTV)
        fun bind(movie: Movie){
            val title = titleMovieTV.text.toString() + movie.title
            val rate = rateMovieTV.text.toString() + movie.rate
            val overview = overviewMovieTV.text.toString() + movie.overview
            Glide.with(itemView)
                .load(movie.poster)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(posterMovieIV)
            titleMovieTV.text = title
            rateMovieTV.text = rate
            overviewMovieTV.text = overview
        }
    }
    class MovieDiffCallBack: DiffUtil.ItemCallback<Movie>(){
        override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem.poster == newItem.poster &&
                    oldItem.title == newItem.title &&
                    oldItem.rate == newItem.rate &&
                    oldItem.overview == newItem.overview
        }

        override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
            return oldItem == newItem
        }
    }

}