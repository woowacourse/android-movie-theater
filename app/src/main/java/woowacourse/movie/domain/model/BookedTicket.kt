package woowacourse.movie.domain.model

import java.io.Serializable

class BookedTicket(
    val theaterName: String,
    val movieTitle: String,
    val movieSchedule: MovieSchedule,
    val headcount: Headcount,
) : Serializable {
    fun totalPrice(): Int = movieSchedule.seats.totalPrice()
}
