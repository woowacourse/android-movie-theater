package woowacourse.movie.view.mapper

import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.view.model.ReservationUiModel

fun Reservation.toUiModel(): ReservationUiModel = ReservationUiModel(
    title,
    screeningDateTime,
    seats.size,
    seats.map { it.toUiModel().seatId },
    price.price
)
