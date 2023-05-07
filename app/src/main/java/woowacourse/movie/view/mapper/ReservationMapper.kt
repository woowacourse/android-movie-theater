package woowacourse.movie.view.mapper

import woowacourse.movie.domain.Reservation
import woowacourse.movie.view.model.ReservationUiModel

fun Reservation.toUiModel(): ReservationUiModel = ReservationUiModel(
    theaterName,
    movieTitle,
    screeningDateTime,
    seats.size,
    seats.map { it.toUiModel().name },
    finalReservationFee.amount
)
