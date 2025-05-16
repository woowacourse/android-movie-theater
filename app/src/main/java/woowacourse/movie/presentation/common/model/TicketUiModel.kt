package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.ticketing.Ticket
import woowacourse.movie.presentation.common.extension.toDateTimeFormatter
import java.time.LocalDateTime

@Parcelize
data class TicketUiModel(
    val title: String,
    val theaterName: String,
    val dateTime: LocalDateTime,
    val seats: List<SeatUiModel>,
    val count: Int,
    val price: Int,
) : Parcelable {
    @IgnoredOnParcel
    val joinedLabel: String = seats.joinToString { it.toLabel() }

    fun formatDateTime(formatPattern: String): String = dateTime.format(formatPattern.toDateTimeFormatter())
}

fun Ticket.toUiModel() =
    TicketUiModel(
        title,
        theaterName,
        reservationDateTime,
        seats.map { it.toUiModel() },
        count,
        price,
    )
