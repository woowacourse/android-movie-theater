package woowacourse.movie.view.handler

import woowacourse.movie.view.movies.MovieListContract
import woowacourse.movie.view.movies.adapter.MovieAdapter

class MovieAdapterEventHandler(
    private val presenter: MovieListContract.Presenter,
) : MovieAdapter.Handler {
    override fun onClickBooking(movieId: Int) {
        presenter.loadTheaters(movieId)
    }
}
