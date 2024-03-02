package com.recon.filmaster.Utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.recon.filmaster.R


//wakaTest
class MovieAdapter(
    private val onLoadMoreListener: OnLoadMoreListener? = null,
            private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private val VIEW_TYPE_ITEM = 0
    private val VIEW_TYPE_LOADING = 1

    private var movies: MutableList<MovieModel> = mutableListOf()
    private var isLoading = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            VIEW_TYPE_ITEM -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.film_mini_layout, parent, false)
                MovieViewHolder(view)
            }
            VIEW_TYPE_LOADING -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.loading_item_layout, parent, false)
                LoadingViewHolder(view)
            }
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MovieViewHolder) {
            holder.bind(movies[position])
        }

        if (!isLoading && position >= itemCount - 1) {
            onLoadMoreListener?.onLoadMore()
            isLoading = true
        }
    }

    override fun getItemCount(): Int {
        return movies.size + if (isLoading) 1 else 0
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == movies.size) VIEW_TYPE_LOADING else VIEW_TYPE_ITEM
    }

    fun addMovies(newMovies: List<MovieModel>) {
        val insertPosition = movies.size
        movies.addAll(newMovies)
        notifyItemRangeInserted(insertPosition, newMovies.size)
    }

    fun setLoaded() {
        isLoading = false
    }

    inner class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleTextView: TextView = itemView.findViewById(R.id.nameTv)
        private val genreTextView: TextView = itemView.findViewById(R.id.genreTv)
        private val releaseYearTextView: TextView = itemView.findViewById(R.id.yearTv)
        private val ratingTV : TextView = itemView.findViewById(R.id.ratingTv)
        private val imageView: ImageView = itemView.findViewById(R.id.imageView)
        private val descTv : TextView = itemView.findViewById(R.id.descTv)

        init {
            itemView.setOnClickListener{
                val dialog = CustomDialogFragment.newInstance(titleTextView.text.toString(),
                    descTv.text.toString())
                dialog.show(fragmentManager, "CustomDialog")
            }
        }

        fun bind(movie: MovieModel) {
            titleTextView.text = movie.title
            genreTextView.text = getGenreDescription(movie.genreIds) // Используйте функцию getGenreDescription для получения строкового представления жанров
            releaseYearTextView.text = movie.releaseYear
            ratingTV.text = movie.rating.toString()
            descTv.text = movie.description

            // Загрузка постера фильма с использованием Glide или другой библиотеки
            Glide.with(itemView)
                .load(movie.posterPath)
                .placeholder(R.drawable.film_placeholder)
                .into(imageView)
        }
    }


    inner class LoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val progressBar: ImageView = itemView.findViewById(R.id.progressBar)
    }

    interface OnLoadMoreListener {
        fun onLoadMore()
    }

}
