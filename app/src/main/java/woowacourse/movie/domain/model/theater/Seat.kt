package woowacourse.movie.domain.model.theater

import woowacourse.movie.domain.model.movie.TicketType
import java.io.Serializable

data class Seat(
    val row: Int,
    val col: Int,
    val ticketType: TicketType = TicketType.B_GRADE,
) : Serializable {
    fun price(): Int = ticketType.price
}
