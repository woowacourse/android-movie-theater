package woowacourse.movie.domain.model.reservation

class MovieTicket(
    val ticketId: Long,
    val reservationMovieInfo: ReservationMovieInfo,
    val reservationInfo: ReservationInfo,
)
