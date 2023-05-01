package woowacourse.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.domain.PaymentType
import java.time.LocalDateTime

@Parcelize
data class ReservationUiModel(
    val id: Long,
    val tickets: Set<TicketUiModel>,
    val paymentType: PaymentType = PaymentType.OFFLINE,
    val payment: Int,
    val movieId: Long,
    val movieTitle: String,
    val bookedDateTime: LocalDateTime,
    val count: Int,
) : Parcelable {
    val seatPosition: String
        get() = tickets.joinToString(", ") { it.coordinate }
}
