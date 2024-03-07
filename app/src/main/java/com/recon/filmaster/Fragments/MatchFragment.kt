package com.recon.filmaster.Fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.recon.filmaster.R
import com.recon.filmaster.Utils.MovieModel
import com.recon.filmaster.Utils.OnSwipeTouchListener
import com.recon.filmaster.Utils.VolleyRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


class MatchFragment : Fragment() {

    private lateinit var card: ScrollView
    lateinit var nameTv: TextView
    lateinit var descTv: TextView
    lateinit var imgHolder: ImageView
    lateinit var filmRatingTv: TextView
    var urlPage: Int = 1
    private lateinit var movies: List<MovieModel>
    private var currentIndex: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_match, container, false)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        card = view.findViewById(R.id.mainLayout)
        nameTv = view.findViewById(R.id.filmNameTV)
        descTv = view.findViewById(R.id.filmDescTV)
        imgHolder = view.findViewById(R.id.imageView3)
        filmRatingTv = view.findViewById(R.id.filmRatingTv)
        loadMovies()

        card.setOnTouchListener(object : OnSwipeTouchListener(this.requireContext()) {
            @SuppressLint("ClickableViewAccessibility")
            override fun onSwipeLeft() {
                super.onSwipeLeft()
                currentIndex++
                showNextMovie()
//                Toast.makeText(
//                    requireContext(), "Swipe Left gesture detected",
//                    Toast.LENGTH_SHORT
//                )
//                    .show()
            }

            override fun onSwipeRight() {
                super.onSwipeRight()
                currentIndex++
                showNextMovie()
//                Toast.makeText(
//                    requireContext(),
//                    "Swipe Right gesture detected",
//                    Toast.LENGTH_SHORT
//                ).show()
            }
        })

    }

    private fun loadMovies() {
        val tmdbUrl = "https://api.themoviedb.org/3/movie/popular?api_key=8204c5848f17f707ac20296bc8c89207&language=ru&page=$urlPage"
        val volleyRequest = VolleyRequest(requireContext())

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = volleyRequest.getJsonFromUrl(tmdbUrl)
                val movies = parseMovies(response)
                this@MatchFragment.movies = movies
                showNextMovie()
            } catch (e: Exception) {
                e.printStackTrace()
                // Handle error
            }
        }
    }

    private fun parseMovies(response: JSONObject): List<MovieModel> {
        val movies = mutableListOf<MovieModel>()
        val resultsArray = response.getJSONArray("results")

        for (i in 0 until resultsArray.length()) {
            val movieObject = resultsArray.getJSONObject(i)
            val id = movieObject.getInt("id")
            val title = movieObject.getString("title")
            val description = movieObject.getString("overview")
            val genreIdsArray = movieObject.getJSONArray("genre_ids")
            val genreIds = mutableListOf<Int>()
            for (j in 0 until genreIdsArray.length()) {
                genreIds.add(genreIdsArray.getInt(j))
            }
            val releaseYear = movieObject.getString("release_date").substring(0, 4)
            val rating = movieObject.getDouble("vote_average")
            val posterPath = "https://image.tmdb.org/t/p/w500${movieObject.getString("poster_path")}"

            val movie = MovieModel(id, title, genreIds, releaseYear, rating, posterPath, description)
            movies.add(movie)
        }

        return movies
    }

    private fun showNextMovie() {
        if (currentIndex < movies.size) {
            val movie = movies[currentIndex]
            nameTv.text = movie.title
            filmRatingTv.text = movie.rating.toString()
            if(movie.description != ""){
                descTv.text = movie.description
            } else {
                descTv.text = "* Нет Описания *"
            }

            Glide.with(requireContext())
                .load(movie.posterPath)
                .placeholder(R.drawable.film_placeholder)
                .into(imgHolder)

        } else {
            urlPage++
            currentIndex = 0
            loadMovies()
            Toast.makeText(requireContext(), "Page : $urlPage", Toast.LENGTH_SHORT).show()
        }
    }

}