package woowacourse.movie.feature.detail

import woowacourse.movie.model.CountState
import woowacourse.movie.model.ReservationState
import woowacourse.movie.model.TheaterMovieState
import java.time.LocalDateTime

interface MovieDetailContract {
    interface View {
        fun navigateSeatSelect(reservationState: ReservationState)
    }

    interface Presenter {
        fun clickConfirm(
            theaterMovieState: TheaterMovieState,
            dateTime: LocalDateTime,
            reservationCount: CountState
        )
    }
}
