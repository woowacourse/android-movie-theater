package woowacourse.movie.domain.ticket

import woowacourse.movie.domain.reservation.PurchaseType
import java.io.Serializable
import java.time.LocalDateTime

data class Ticket(
    val id: Long? = null,
    val title: String,
    val count: Int,
    val showtime: LocalDateTime,
    val cinemaName: String,
    val purchaseType: PurchaseType = PurchaseType.DEFAULT,
) : Serializable {
    val price: Int = count * TICKET_PRICE

    fun notifyBeforeMinutes(): LocalDateTime =
        when (purchaseType) {
            PurchaseType.DEFAULT -> showtime.minusMinutes(DEFAULT_NOTIFY_BEFORE_MINUTES)
        }

    companion object {
        const val TICKET_PRICE = 13_000
        private const val DEFAULT_NOTIFY_BEFORE_MINUTES = 30L
    }
}
