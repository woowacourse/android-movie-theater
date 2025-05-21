package woowacourse.movie.view.reservation

import woowacourse.movie.domain.PurchasePolicy
import woowacourse.movie.domain.movieseat.Seats
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TicketUi(
    val title: String,
    val date: LocalDateTime,
    val personnel: Int,
    val theaterName: String,
    val seats: Seats = Seats(mutableSetOf()),
) : Serializable {
    fun formattedDate(
        purchasePolicy: PurchasePolicy,
        formatPattern: String,
    ): String {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        val timeText = purchasePolicy.time.format(formatter)
        return timeText
    }

    fun formattedReservationInfo(): String {
        val dateFormatter = DateTimeFormatter.ofPattern("yyyy.MM.d | HH:mm")
        val timeText = date.format(dateFormatter)

        return "$timeText | ${this.theaterName}"
    }
}
