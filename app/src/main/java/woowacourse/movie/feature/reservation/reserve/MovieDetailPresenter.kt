package woowacourse.movie.feature.reservation.reserve

import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.SelectTheaterAndMovieState
import java.time.LocalDateTime

class MovieDetailPresenter(
    val view: MovieDetailContract.View
) : MovieDetailContract.Presenter {

    override fun navigateSeatSelectActivity(
        theaterAndMovieState: SelectTheaterAndMovieState,
        dateTime: LocalDateTime,
        reservationCount: Int
    ) {
        val reservationState = SelectReservationState(
            theaterAndMovieState.theater,
            theaterAndMovieState.movie,
            dateTime,
            reservationCount
        )
        view.showSeatSelectActivity(reservationState)
    }
}
