package woowacourse.movie.ui.fragment.movieList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.data.AdvRepository
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adv.AdvDetailActivity
import woowacourse.movie.ui.main.adapter.MovieListAdapter
import woowacourse.movie.ui.main.itemModel.AdvItemModel
import woowacourse.movie.ui.main.itemModel.MovieItemModel
import woowacourse.movie.ui.reservation.MovieDetailActivity

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {

    private var movieListView: RecyclerView? = null
    private var adapter: MovieListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MovieListAdapter(
            movie = MovieRepository.allMovies().map(::MovieItemModel),
            adv = AdvRepository.allAdv().map(::AdvItemModel),
            onClickMovie = { movieItemModel -> navigateMovieDetail(movieItemModel.movieState) },
            onClickAdv = { advItemModel -> navigateAdbDetail(advItemModel.advState) }
        )

        movieListView = view.findViewById(R.id.rv_main)
        movieListView?.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        movieListView = null
        adapter = null
    }

    private fun navigateMovieDetail(movie: MovieState) {
        MovieDetailActivity.startActivity(activity as Context, movie)
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        AdvDetailActivity.startActivity(activity as Context, adbState)
    }
}
