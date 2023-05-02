package woowacourse.movie.view.moviemain.movielist

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.MovieMockRepository
import woowacourse.movie.domain.Movie
import woowacourse.movie.view.ReservationActivity
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieListModel

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val movies = MovieMockRepository.findAll()
        val dataList = generateMovieListData(movies)

        val movieAdapter = MovieListAdapter(
            dataList = dataList
        ) { item ->
            when (item) {
                is MovieListModel.MovieUiModel -> {
                    val intent = ReservationActivity.newIntent(requireContext(), item)
                    startActivity(intent)
                }
                is MovieListModel.MovieAdModel -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(item.url))
                    startActivity(intent)
                }
            }
        }
        val movieListView = view.findViewById<RecyclerView>(R.id.movie_recyclerview)
        movieListView.adapter = movieAdapter
    }

    private fun generateMovieListData(movies: List<Movie>): List<MovieListModel> {
        val ad = MovieListModel.MovieAdModel(
            R.drawable.woowacourse_banner,
            "https://woowacourse.github.io/"
        )

        return mixMovieAdData(movies, ad, AD_POST_INTERVAL)
    }

    private fun mixMovieAdData(
        movies: List<Movie>,
        ad: MovieListModel.MovieAdModel,
        adPostInterval: Int
    ): List<MovieListModel> {
        return movies.flatMapIndexed { index, movie ->
            if (index % adPostInterval == adPostInterval - 1) {
                listOf(movie.toUiModel(), ad)
            } else {
                listOf(movie.toUiModel())
            }
        }
    }

    companion object {
        private const val AD_POST_INTERVAL = 3
    }
}
