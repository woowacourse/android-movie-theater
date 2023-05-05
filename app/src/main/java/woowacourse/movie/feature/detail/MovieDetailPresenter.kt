package woowacourse.movie.feature.detail

import woowacourse.movie.model.CountState
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.TheaterMovieState
import java.time.LocalDateTime

class MovieDetailPresenter(
    val view: MovieDetailContract.View
) : MovieDetailContract.Presenter {
    override fun clickConfirm(
        theaterMovie: TheaterMovieState,
        dateTime: LocalDateTime,
        reservationCount: CountState
    ) {
        val reservationState = ReservationState(
            theaterMovie.theaterName,
            theaterMovie.movie,
            dateTime,
            reservationCount
        )
        view.navigateSeatSelect(reservationState)
    }
}
