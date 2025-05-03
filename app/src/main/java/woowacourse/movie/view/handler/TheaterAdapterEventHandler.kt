package woowacourse.movie.view.handler

import woowacourse.movie.view.home.movies.MovieListContract
import woowacourse.movie.view.home.movies.adapter.TheaterAdapter

class TheaterAdapterEventHandler(
    private val presenter: MovieListContract.Presenter,
) : TheaterAdapter.Handler {
    override fun onSelectTheater(
        theaterName: String,
        movieId: Int,
    ) {
        presenter.loadMovieScreening(movieId, theaterName)
    }
}
