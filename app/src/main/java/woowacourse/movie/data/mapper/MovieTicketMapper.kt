package woowacourse.movie.data.mapper

import woowacourse.movie.data.model.MovieTicketEntity
import woowacourse.movie.domain.model.movieticket.MovieTicket

fun MovieTicketEntity.toDomainModel(): MovieTicket {
    return MovieTicket(
        id = this.id,
        theaterName = this.theaterName,
        movieTitle = this.movieTitle,
        screeningDate = this.screeningDate,
        screeningTime = this.screeningTime,
        reservationCount = this.reservationCount,
        reservationSeats = this.reservationSeats,
        totalPrice = this.totalPrice
    )
}

fun MovieTicket.toEntity(): MovieTicketEntity {
    return MovieTicketEntity(
        id = this.id,
        theaterName = this.theaterName,
        movieTitle = this.movieTitle,
        screeningDate = this.screeningDate,
        screeningTime = this.screeningTime,
        reservationCount = this.reservationCount,
        reservationSeats = this.reservationSeats,
        totalPrice = this.totalPrice
    )
}
