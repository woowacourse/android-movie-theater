package woowacourse.movie.feature.reservation.reserve

import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.SelectTheaterAndMovieState
import java.time.LocalDateTime

interface MovieDetailContract {
    interface View {
        fun showSeatSelectActivity(reservationState: SelectReservationState)
    }

    interface Presenter {
        fun navigateSeatSelectActivity(
            theaterAndMovieState: SelectTheaterAndMovieState,
            dateTime: LocalDateTime,
            reservationCount: Int
        )
    }
}
