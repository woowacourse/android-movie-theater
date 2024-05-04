package woowacourse.movie.feature.detail

import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.feature.detail.MovieDetailContract
import woowacourse.movie.model.MovieCount

class MovieDetailPresenter(
    private val view: MovieDetailContract.View,
) : MovieDetailContract.Presenter {
    private var movieCount: MovieCount = MovieCount()

    override fun loadMovieDetail(movieId: Long) {
        val movie = getMovieById(movieId) ?: return
        view.displayMovieDetail(movie)
        view.updateCountView(movieCount.count)
    }

    override fun updateReservationCount(count: Int) {
        movieCount = movieCount.update(count)
        view.updateCountView(movieCount.count)
    }

    override fun plusReservationCount() {
        movieCount = ++movieCount
        view.updateCountView(movieCount.count)
    }

    override fun minusReservationCount() {
        movieCount = --movieCount
        view.updateCountView(movieCount.count)
    }

    override fun reserveMovie(
        id: Long,
        date: String,
        time: String,
    ) {
        view.navigateToSeatSelectionView(id, date, time, movieCount.count)
    }
}
