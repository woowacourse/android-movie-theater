package woowacourse.movie.view.movie

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.MovieDao
import woowacourse.movie.view.ReservationUiFormatter
import woowacourse.movie.view.model.MovieUiModel

class MoviePresenter(
    val view: MovieContract.View,
) : MovieContract.Presenter {
    override fun fetchMovies() {
        val movies = MovieDao().getShowingMovies().map { it.toUiModel() }
        view.showMovies(movies)
    }

    override fun reservationSelected(movie: MovieUiModel) {
        view.showTheaterInfo(movie)
    }

    private fun Movie.toUiModel(): MovieUiModel =
        MovieUiModel(
            name = this.title,
            poster = this.poster,
            startDate = ReservationUiFormatter.localDateToUI(this.startDate),
            endDate = ReservationUiFormatter.localDateToUI(this.endDate),
            runningTime = this.runningTime,
        )
}
