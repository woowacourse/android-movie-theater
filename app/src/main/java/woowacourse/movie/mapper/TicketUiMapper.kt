package woowacourse.movie.mapper

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel
import woowacourse.movie.util.Formatter.formatDateDotSeparated
import woowacourse.movie.util.Formatter.formatMoney
import woowacourse.movie.util.Formatter.formatStringDateDotSeparated
import woowacourse.movie.util.Formatter.formatStringTimeWithMidnight24
import woowacourse.movie.util.Formatter.formatTimeWithMidnight24

fun Ticket.toUiModel(): TicketUiModel {
    val selectedDateText = formatDateDotSeparated(selectedDate)
    val selectedTimeText = formatTimeWithMidnight24(selectedTime)
    val amountText = formatMoney(amount)

    return TicketUiModel(
        theater = theater,
        title = title,
        headCount = headCount.value,
        selectedDateText = selectedDateText,
        selectedTimeText = selectedTimeText,
        totalPrice = amountText,
        seats = seats.seats.map { it.toUiModel() }.toSet(),
    )
}

fun TicketUiModel.toDomain(): Ticket {
    val selectedDate = formatStringDateDotSeparated(selectedDateText)
    val selectedTime = formatStringTimeWithMidnight24(selectedTimeText)

    return Ticket(
        theater = theater,
        title = title,
        headCount = HeadCount(headCount),
        selectedDate = selectedDate,
        selectedTime = selectedTime,
        seats = Seats(seats.map { it.toDomain() }.toSet()),
    )
}
