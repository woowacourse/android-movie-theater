package woowacourse.movie.model.ticket

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.db.history.ReservationHistoryDatabase.Companion.RESERVATION_TICKET_DB_NAME
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.SeatSelection
import java.io.Serializable

@Entity(tableName = RESERVATION_TICKET_DB_NAME)
data class ReservationTicket(
    @PrimaryKey(autoGenerate = true)
    val ticketId: Long = 0,
    val movieId: Int,
    val theaterId: Int,
    val movieTitle: String,
    val theaterName: String,
    val seatSelection: SeatSelection,
    val screeningDateTime: ScreeningDateTime,
    val amount: Int,
) : Serializable {
    fun toTicket(): Ticket {
        return Ticket(
            movieId = this.movieId,
            theaterId = this.theaterId,
            seatSelection = this.seatSelection,
            screeningDateTime = this.screeningDateTime,
            amount = this.amount,
        )
    }
}
