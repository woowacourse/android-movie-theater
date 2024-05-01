package woowacourse.movie.model.ticket

import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Seats
import java.io.Serializable

data class Ticket(
    val movieId: Int,
    val theaterId: Int,
    val seats: Seats,
    val screeningDateTime: ScreeningDateTime,
    val amount: Int,
) : Serializable
