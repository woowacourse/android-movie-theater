package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.ticket.Ticket
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Parcelize
class TicketUiModel(
    val title: String,
    val theaterName: String,
    val dateTime: LocalDateTime,
    val seats: List<SeatUiModel>,
    val count: Int,
    val price: Int,
) : Parcelable {
    @IgnoredOnParcel
    val joinedLabel: String = seats.joinToString { it.toLabel() }

    fun formatedDateTime(formatPattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        return dateTime.format(formatter)
    }
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
