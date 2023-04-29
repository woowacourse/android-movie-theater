package woowacourse.movie.ui.fragment.movieList

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.main.MainActivity
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adapter.MovieListAdapter
import woowacourse.movie.ui.adv.AdvDetailActivity
import woowacourse.movie.ui.itemModel.AdvItemModel
import woowacourse.movie.ui.itemModel.MovieItemModel
import woowacourse.movie.ui.reservation.MovieDetailActivity

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private lateinit var movieListView: RecyclerView
    private lateinit var adapter: MovieListAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        movieListView = view.findViewById(R.id.rv_main)
        adapter = MovieListAdapter(
            movie = MovieRepository.allMovies().map {
                it.toItemModel { position ->
                    navigateMovieDetail((adapter.items[position] as MovieItemModel).movieState)
                }
            },
            adv = AdvRepository.allAdv().map {
                it.toItemModel { position ->
                    navigateAdbDetail((adapter.items[position] as AdvItemModel).advState)
                }
            }
        )
        movieListView.adapter = adapter
    }

    private fun navigateMovieDetail(movie: MovieState) {
        val intent = Intent(activity, MovieDetailActivity::class.java)
        intent.putExtra(MainActivity.KEY_MOVIE, movie)
        startActivity(intent)
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        val intent = Intent(activity, AdvDetailActivity::class.java)
        intent.putExtra(MainActivity.KEY_ADV, adbState)
        startActivity(intent)
    }
}
