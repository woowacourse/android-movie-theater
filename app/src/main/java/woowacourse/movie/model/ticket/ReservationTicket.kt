package woowacourse.movie.model.ticket

import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import java.io.Serializable

data class ReservationTicket(
    val movieTitle: String,
    val theaterName: String,
    val seats: Seats,
    val screeningDateTime: ScreeningDateTime,
    val amount: Int,
): Serializable
