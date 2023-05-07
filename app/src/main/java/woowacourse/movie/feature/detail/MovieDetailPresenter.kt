package woowacourse.movie.feature.detail

import woowacourse.movie.model.CountState
import woowacourse.movie.model.SelectReservationState
import woowacourse.movie.model.SelectTheaterAndMovieState
import java.time.LocalDateTime

class MovieDetailPresenter(
    val view: MovieDetailContract.View
) : MovieDetailContract.Presenter {
    override fun clickConfirm(
        theaterMovie: SelectTheaterAndMovieState,
        dateTime: LocalDateTime,
        reservationCount: CountState
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
