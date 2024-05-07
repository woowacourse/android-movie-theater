package woowacourse.movie.data.mapper

import woowacourse.movie.data.model.ReservationEntity
import woowacourse.movie.domain.model.Reservation

fun ReservationEntity.toDomain(): Reservation =
    Reservation(
        reservationId = reservationId,
        theaterId = theaterId,
        movieId = movieId,
        title = title,
        ticketCount = ticketCount,
        seats = seats,
        dateTime = dateTime,
    )

fun List<ReservationEntity>.toDomain(): List<Reservation> =
    map { reservationEntity ->
        reservationEntity.toDomain()
    }
