package com.recon.filmaster

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.recon.filmaster.Utils.MovieAdapter
import com.recon.filmaster.Utils.MovieModel
import com.recon.filmaster.Utils.VolleyRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class TopMoviesFragment : Fragment(), MovieAdapter.OnLoadMoreListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: MovieAdapter
    private var page = 1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_top_movies, container, false)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = MovieAdapter(this)
        recyclerView.adapter = adapter

        loadMovies()

        return view
    }

    override fun onLoadMore() {
        page++
        loadMovies()
    }

    private fun loadMovies() {
        val tmdbUrl = "https://api.themoviedb.org/3/movie/popular?api_key=8204c5848f17f707ac20296bc8c89207&language=ru&page=$page"
        val volleyRequest = VolleyRequest(requireContext())

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val response = volleyRequest.getJsonFromUrl(tmdbUrl)
                val movies = parseMovies(response)
                adapter.addMovies(movies)
                adapter.setLoaded()
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
            val genreIdsArray = movieObject.getJSONArray("genre_ids")
            val genreIds = mutableListOf<Int>()
            for (j in 0 until genreIdsArray.length()) {
                genreIds.add(genreIdsArray.getInt(j))
            }
            val releaseYear = movieObject.getString("release_date").substring(0, 4)
            val rating = movieObject.getDouble("vote_average")
            val posterPath = "https://image.tmdb.org/t/p/w500${movieObject.getString("poster_path")}"

            val movie = MovieModel(id, title, genreIds, releaseYear, rating, posterPath)
            movies.add(movie)
        }

        return movies
    }
}
