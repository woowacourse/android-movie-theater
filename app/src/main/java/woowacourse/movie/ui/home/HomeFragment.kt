package woowacourse.movie.ui.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.MovieListModel
import woowacourse.movie.ui.home.adapter.ItemClickListener
import woowacourse.movie.ui.home.adapter.MovieListAdapter
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
import woowacourse.movie.utils.MockData

class HomeFragment : Fragment() {
    private lateinit var moviesView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)

        setMovieList(view, MockData.getMoviesWithAds())

        return view
    }

    private fun setMovieList(view: View, movies: List<MovieListModel>) {
        moviesView = view.findViewById(R.id.main_movie_list)
        moviesView.adapter = MovieListAdapter(
            movies,
            object : ItemClickListener {
                override fun onMovieItemClick(movie: MovieListModel.MovieModel) {
                    moveToDetailActivity(movie)
                }

                override fun onAdItemClick(ad: MovieListModel.AdModel) {
                    moveToWebPage(ad)
                }
            },
        )
    }

    private fun moveToDetailActivity(movie: MovieListModel.MovieModel) {
        val intent = Intent(context, MovieDetailActivity::class.java)
        intent.putExtra(KEY_MOVIE, movie)
        startActivity(intent)
    }

    private fun moveToWebPage(ad: MovieListModel.AdModel) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ad.url))
        startActivity(intent)
    }

    companion object {
        const val KEY_MOVIE = "movie"
    }
}
