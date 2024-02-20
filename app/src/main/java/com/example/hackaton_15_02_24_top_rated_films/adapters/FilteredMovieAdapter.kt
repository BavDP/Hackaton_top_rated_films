package com.example.hackaton_15_02_24_top_rated_films.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.hackaton_15_02_24_top_rated_films.R
import com.example.hackaton_15_02_24_top_rated_films.databinding.MovieLayoutBinding
import com.example.hackaton_15_02_24_top_rated_films.models.Movie

class FilteredMovieAdapter : RecyclerView.Adapter<FilteredMovieAdapter.FilteredMovieViewHolder>() {

    var movies: List<Movie> = emptyList()
        set(newValue) {
            val diffCallback = MovieDiffCallback(field, newValue)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            field = newValue
            diffResult.dispatchUpdatesTo(this)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilteredMovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieLayoutBinding.inflate(inflater, parent, false)
        return FilteredMovieViewHolder(binding)
    }

    override fun getItemCount(): Int = movies.size

    override fun onBindViewHolder(holder: FilteredMovieViewHolder, position: Int) {
        val movie = movies[position]
        holder.bind(movie)
    }

    class FilteredMovieViewHolder(
        private val binding: MovieLayoutBinding
    ) : ViewHolder(binding.root) {

        fun bind(movie: Movie) {
            binding.titleMovieTV.text = movie.title
            binding.rateMovieTV.text = movie.rate.toString()
            binding.overviewMovieTV.text = movie.overview
            Glide.with(binding.root.context)
                .load(BASE_PATH + movie.poster)
                .placeholder(R.drawable.ic_launcher_foreground)
                .error(R.drawable.ic_launcher_foreground)
                .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                .into(binding.posterMovieIV)
        }
    }

    class MovieDiffCallback(
        private val oldList: List<Movie>,
        private val newList: List<Movie>,
    ) : DiffUtil.Callback() {
        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldItem = oldList[oldItemPosition]
            val newItem = newList[newItemPosition]
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}