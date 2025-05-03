package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.Headcount
import java.io.Serializable
import java.time.LocalDateTime

class BookedTicket(
    val movieName: String,
    val headcount: Headcount,
    val dateTime: LocalDateTime,
    val seats: Seats,
    val theaterName: String,
) : Serializable {
    fun totalPrice(): Int = seats.totalPrice()
}
