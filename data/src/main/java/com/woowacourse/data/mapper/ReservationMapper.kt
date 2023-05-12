package com.woowacourse.data.mapper

import com.woowacourse.data.model.DataReservation
import woowacourse.movie.domain.model.reservation.DomainReservation

fun DataReservation.toDomain(): DomainReservation =
    DomainReservation(
        movieTitle = movieTitle,
        theaterName = theaterName,
        movieTime = movieTime.toDomain(),
        movieDate = movieDate.toDomain(),
        ticket = ticket.toDomain(),
        seats = seats.toDomain(),
        ticketPrice = ticketPrice.toDomain()
    )

fun DomainReservation.toData(): DataReservation =
    DataReservation(
        movieTitle = movieTitle,
        theaterName = theaterName,
        movieTime = movieTime.toData(),
        movieDate = movieDate.toData(),
        ticket = ticket.toData(),
        seats = seats.toData(),
        ticketPrice = ticketPrice.toData()
    )
