package woowacourse.movie.ui.fragment.movieList

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import woowacourse.movie.R
import woowacourse.movie.model.AdvState
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.adapter.MovieListAdapter
import woowacourse.movie.ui.adv.AdvDetailActivity
import woowacourse.movie.ui.fragment.cinemaBottomSheet.CinemaListBottomSheet
import woowacourse.movie.ui.itemModel.AdvItemModel
import woowacourse.movie.ui.itemModel.MovieItemModel

class MovieListFragment : Fragment(R.layout.fragment_movie_list) {
    private lateinit var presenter: MovieListContract.Presenter

    private var movieListView: RecyclerView? = null
    private var adapter: MovieListAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = MovieListPresenter()

        adapter = MovieListAdapter(
            movie = presenter.getMovieList().map(::MovieItemModel),
            adv = presenter.getAdvList().map(::AdvItemModel),
            onClickMovie = { movieItemModel -> showCinemaBottomSheet(movieItemModel.movieState) },
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

    private fun showCinemaBottomSheet(movie: MovieState) {
        CinemaListBottomSheet(movie)
            .show(parentFragmentManager, CinemaListBottomSheet.TAG_CINEMA_LIST_BOTTOM_SHEET)
    }

    private fun navigateAdbDetail(adbState: AdvState) {
        AdvDetailActivity.startActivity(activity as Context, adbState)
    }
}
