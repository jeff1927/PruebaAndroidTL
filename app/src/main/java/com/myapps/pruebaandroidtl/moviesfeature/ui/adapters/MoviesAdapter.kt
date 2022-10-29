package com.myapps.pruebaandroidtl.moviesfeature.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.myapps.pruebaandroidtl.databinding.MovieItemLayoutBinding
import com.myapps.pruebaandroidtl.moviesfeature.domain.models.MovieModel

class MoviesAdapter(private var setOnItemClicked: (movie: MovieModel) -> Unit): PagingDataAdapter<MovieModel,MoviesAdapter.MoviesViewHolder>(MovieDifferCallBack()) {

    class MoviesViewHolder(val binding: MovieItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesViewHolder {
        val view =
            MovieItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return MoviesViewHolder(view)
    }

    override fun onBindViewHolder(holder: MoviesViewHolder, position: Int) {
        val movie = getItem(position)
        holder.binding.apply {
            movie?.let {
                tvTitle.text = movie.title
                tvDescription.text = movie.overview
                Glide.with(this.root).load("https://image.tmdb.org/t/p/w500" + it.poster_path)
                    .into(ivItemImage)
                holder.itemView.setOnClickListener {
                    setOnItemClicked.invoke(movie)
                }
            }
        }
    }
}

class MovieDifferCallBack : DiffUtil.ItemCallback<MovieModel>() {
    override fun areItemsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: MovieModel, newItem: MovieModel): Boolean {
        return oldItem == oldItem
    }

}
