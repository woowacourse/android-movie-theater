package woowacourse.movie.domain.model.reservation

import woowacourse.movie.data.db.ReservationTicketEntity

data class MovieTicket(
    val ticketId: Long,
    val reservationMovieInfo: ReservationMovieInfo,
    val reservationInfo: ReservationInfo,
)

fun MovieTicket.toReservationTicketEntity(
    selectDate: String,
    screenTime: String,
): ReservationTicketEntity {
    return ReservationTicketEntity(
        ticketId = this.ticketId,
        movieTitle = this.reservationMovieInfo.title,
        theaterName = this.reservationMovieInfo.theaterName,
        screenDate = selectDate,
        screenTime = screenTime,
        selectedSeats = this.reservationInfo.selectedSeats,
    )
}
