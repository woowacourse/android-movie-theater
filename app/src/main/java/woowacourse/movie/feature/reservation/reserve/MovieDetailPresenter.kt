package woowacourse.movie.feature.reservation.reserve

import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.SelectTheaterAndMovieState
import java.time.LocalDateTime

class MovieDetailPresenter(
    val view: MovieDetailContract.View
) : MovieDetailContract.Presenter {
    override fun clickConfirm(
        theaterMovie: SelectTheaterAndMovieState,
        dateTime: LocalDateTime,
        reservationCount: Int
    ) {
        val reservationState = SelectReservationState(
            theaterMovie.theater,
            theaterMovie.movie,
            dateTime,
            reservationCount
        )
        view.navigateSeatSelect(reservationState)
    }
}
