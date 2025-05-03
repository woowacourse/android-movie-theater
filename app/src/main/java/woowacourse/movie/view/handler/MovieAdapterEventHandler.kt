package woowacourse.movie.view.handler

import android.content.Context
import woowacourse.movie.R
import woowacourse.movie.view.home.movies.MovieListContract
import woowacourse.movie.view.home.movies.adapter.MovieAdapter

class MovieAdapterEventHandler(
    private val context: Context,
    private val presenter: MovieListContract.Presenter,
) : MovieAdapter.Handler {
    override fun onClickBooking(movieId: Int) {
        presenter.loadTheaters(movieId, context.getString(R.string.text_schedule_size))
    }
}
