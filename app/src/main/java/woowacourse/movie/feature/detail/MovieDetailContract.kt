package woowacourse.movie.feature.detail

import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.ReservationState
import java.time.LocalDateTime

interface MovieDetailContract {
    interface View {
        fun navigateSeatSelect(reservationState: ReservationState)
    }

    interface Presenter {
        fun clickConfirm(movie: MovieState, dateTime: LocalDateTime, reservationCount: CountState)
    }
}
