package woowacourse.app.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.app.presentation.model.movie.MovieUiModel
import woowacourse.app.presentation.model.ticket.TicketUiModel
import woowacourse.domain.PaymentType
import java.time.LocalDateTime

@Parcelize
data class ReservationUiModel(
    val id: Long,
    val tickets: Set<TicketUiModel>,
    val paymentType: PaymentType = PaymentType.OFFLINE,
    val payment: Int,
    val movie: MovieUiModel,
    val movieTitle: String,
    val bookedDateTime: LocalDateTime,
    val count: Int,
    val alarmCycle: Long,
) : Parcelable {
    val seatPosition: String
        get() = tickets.joinToString(", ") { it.coordinate }
}
