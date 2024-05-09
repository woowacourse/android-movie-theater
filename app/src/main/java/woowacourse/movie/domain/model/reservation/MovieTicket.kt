package woowacourse.movie.domain.model.reservation

import woowacourse.movie.repository.db.ReservationTicketEntity

data class MovieTicket(
    val ticketId: Long, // 티켓id
    val reservationMovieInfo: ReservationMovieInfo, // 영화제목, 극장이름, 상영정보
    val reservationInfo: ReservationInfo, // 예매 개수랑, 선택된 좌석
)

fun MovieTicket.toReservationTicketEntity(
    movieTitle: String,
    theaterName: String,
): ReservationTicketEntity {
    return ReservationTicketEntity(
        ticketId = this.ticketId,
        movieTitle = reservationMovieInfo.title,
        theaterName = reservationMovieInfo.theaterName,
        screenDate = reservationMovieInfo.dateTime.toString(),
        seats = reservationInfo.selectedSeats
    )
}
