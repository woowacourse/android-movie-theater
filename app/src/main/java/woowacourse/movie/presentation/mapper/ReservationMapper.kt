package woowacourse.movie.presentation.mapper

import woowacourse.movie.domain.model.reservation.DomainReservation
import woowacourse.movie.presentation.model.Reservation

fun Reservation.toDomain(): DomainReservation = DomainReservation.of(
    movieTitle,
    movieDate.year,
    movieDate.month,
    movieDate.day,
    movieTime.hour,
    movieTime.min,
    ticket.count,
    seats.toDomain(),
    ticketPrice.toDomain(),
)

fun DomainReservation.toPresentation(): Reservation = Reservation(
    movieTitle,
    movieDate.toPresentation(),
    movieTime.toPresentation(),
    ticket.toPresentation(),
    seats.toPresentation(),
    ticketPrice.toPresentation()
)
