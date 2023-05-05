package woowacourse.movie.presentation.mapper

import woowacourse.movie.domain.model.reservation.DomainReservation
import woowacourse.movie.presentation.model.Reservation

fun Reservation.toDomain(): DomainReservation = DomainReservation(
    movieTitle,
    theaterName,
    movieDate.toDomain(),
    movieTime.toDomain(),
    ticket.toDomain(),
    seats.toDomain(),
    ticketPrice.toDomain(),
)

fun DomainReservation.toPresentation(): Reservation = Reservation(
    movieTitle,
    theaterName,
    movieDate.toPresentation(),
    movieTime.toPresentation(),
    ticket.toPresentation(),
    seats.toPresentation(),
    ticketPrice.toPresentation()
)
