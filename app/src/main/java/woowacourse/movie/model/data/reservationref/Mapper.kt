package woowacourse.movie.model.data.reservationref

import woowacourse.movie.model.ReservationRef

fun ReservationRefDto.toReservationRef() =
    ReservationRef(
        id,
        screeningRefId,
        seats,
    )

fun ReservationRef.toDto() =
    ReservationRefDto(
        screeningRefId = screeningId,
        seats = seats,
    )
