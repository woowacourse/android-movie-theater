package woowacourse.movie.domain.model

import java.io.Serializable

data class BookedTicket(
    val id: Long? = null,
    val theaterName: String,
    val movieTitle: String,
    val movieSchedule: MovieSchedule,
    val headcount: Headcount,
) : Serializable {
    fun totalPrice(): Int = movieSchedule.seats.totalPrice()
}
