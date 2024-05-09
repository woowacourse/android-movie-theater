package woowacourse.movie.ticket.model

import woowacourse.movie.detail.model.Count
import woowacourse.movie.seats.model.Seat

data class Ticket(
    val movieId: Long,
    val screeningDate: String,
    val screeningTime: String,
    val seatsCount: Count,
    val seats: List<Seat>,
    val theaterId: Long,
    val price: Int,
)
