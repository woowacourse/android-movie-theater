package woowacourse.movie.model.ticket

import woowacourse.movie.db.ticket.TicketEntity
import woowacourse.movie.model.seats.Seats
import java.io.Serializable
import java.time.LocalDateTime

data class Ticket(
    val screeningDateTime: LocalDateTime,
    val movieTitle: String,
    val theaterName: String,
    val seats: Seats,
    var uid: Long? = null,
) : Serializable {
    fun getSeatsAmount() = seats.calculateAmount()

    companion object {
        const val DEFAULT_TICKET_ID = -1L

        fun Ticket.toEntity() =
            TicketEntity(
                screeningDateTime = screeningDateTime,
                movieTitle = movieTitle,
                theaterName = theaterName,
                seats = seats,
                uid = this.uid,
            )
    }
}
