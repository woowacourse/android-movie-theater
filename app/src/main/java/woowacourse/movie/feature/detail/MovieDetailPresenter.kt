package woowacourse.movie.feature.detail

import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.ReservationState
import java.time.LocalDateTime

class MovieDetailPresenter(
    val view: MovieDetailContract.View
) : MovieDetailContract.Presenter {
    override fun clickConfirm(
        movie: MovieState,
        dateTime: LocalDateTime,
        reservationCount: CountState
    ) {
        val reservationState = ReservationState(movie, dateTime, reservationCount)
        view.navigateSeatSelect(reservationState)
    }
}
