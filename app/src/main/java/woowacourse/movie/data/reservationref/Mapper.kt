package woowacourse.movie.data.reservationref

import woowacourse.movie.model.ReservationRef

fun ReservationRefDto.toReservationRef() =
    ReservationRef(
        id,
        screeningRefId,
        seats,
    )

fun ReservationRef.toDto() =
    ReservationRefDto(
        id,
        screeningId,
        seats,
    )
