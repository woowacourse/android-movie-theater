package woowacourse.movie.reservationlist.uimodel

import woowacourse.movie.model.Reservation

fun Reservation.toReservationUiModel(): ReservationUiModel {
    return ReservationUiModel(
        id,
        screening.localDateTime.toLocalDate().toString(),
        screening.localDateTime.toLocalTime().toString(),
        screening.theater.name,
        screening.movie.title,
    )
}
