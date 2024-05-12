package woowacourse.movie.model.ticket

import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.SeatSelection
import java.io.Serializable

data class Ticket(
    val movieId: Int,
    val theaterId: Int,
    val seatSelection: SeatSelection,
    val screeningDateTime: ScreeningDateTime,
    val amount: Int,
) : Serializable {
    fun toReservationTicket(
        movieTitle: String,
        theaterName: String,
    ): ReservationTicket {
        return ReservationTicket(
            theaterId = theaterId,
            movieId = movieId,
            theaterName = theaterName,
            movieTitle = movieTitle,
            seatSelection = seatSelection,
            amount = amount,
            screeningDateTime = screeningDateTime,
        )
    }
}
