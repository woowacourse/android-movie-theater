package woowacourse.movie.domain.model.movieticket

data class MovieTicket(
    val id: Int,
    val theaterName: String,
    val movieTitle: String,
    val screeningSchedule: String,
    val reservationCount: Int,
    val reservationSeats: String,
    val totalPrice: Int,
)
