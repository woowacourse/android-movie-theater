package woowacourse.movie.domain.model.movieticket

data class MovieTicket(
    val id: Long = 0L,
    val theaterName: String,
    val movieTitle: String,
    val screeningDate: String,
    val screeningTime: String,
    val reservationCount: Int,
    val reservationSeats: String,
    val totalPrice: Int
)
