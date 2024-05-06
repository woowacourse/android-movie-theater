package woowacourse.movie.model.ticket

import androidx.room.Entity
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import java.io.Serializable

@Entity(tableName = "reservationTicket")
data class ReservationTicket(
    val movieTitle: String,
    val theaterName: String,
    val seats: Seats,
    val screeningDateTime: ScreeningDateTime,
    val amount: Int,
): Serializable
