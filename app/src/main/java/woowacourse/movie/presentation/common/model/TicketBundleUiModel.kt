package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.ticket.TicketBundle
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Parcelize
class TicketBundleUiModel(
    val title: String,
    val size: Int,
    val dateTime: LocalDateTime,
    val totalPrice: Int,
    val labels: List<SeatUiModel>,
    val theaterName: String,
) : Parcelable {
    @IgnoredOnParcel
    val joinedLabel: String = labels.joinToString { it.toLabel() }

    fun formatedDateTime(formatPattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        return dateTime.format(formatter)
    }
}

fun TicketBundle.toUiModel(theaterName: String): TicketBundleUiModel =
    TicketBundleUiModel(
        title,
        size,
        dateTime,
        totalPrice,
        labels.map { it.toUiModel() },
        theaterName,
    )
