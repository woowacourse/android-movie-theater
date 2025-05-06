package woowacourse.movie.view.movies.adapter

import woowacourse.movie.view.movies.MovieListContract

class MovieAdapterEventHandler(
    private val presenter: MovieListContract.Presenter,
) : MovieAdapter.Handler {
    override fun onClickBooking(movieId: Int) {
        presenter.loadTheaters(movieId)
    }
}
