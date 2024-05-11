package woowacourse.movie.reservationlist.uimodel

import woowacourse.movie.model.Reservation

fun Reservation.toReservationUiModel() =
    ReservationUiModel(
        id,
        this.screening.localDateTime.toLocalDate().toString(),
        this.screening.localDateTime.toLocalTime().toString(),
        screening.theater.name,
        screening.movie.title,
    )
