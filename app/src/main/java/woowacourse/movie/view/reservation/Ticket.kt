package woowacourse.movie.view.reservation

import woowacourse.movie.domain.PurchasePolicy
import java.io.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class Ticket(
    val title: String,
    val date: LocalDateTime,
    val personnel: Int,
    val theaterName: String,
) : Serializable {
    fun formattedDate(
        purchasePolicy: PurchasePolicy,
        formatPattern: String,
    ): String {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        val timeText = purchasePolicy.time.format(formatter)
        return timeText
    }
}
