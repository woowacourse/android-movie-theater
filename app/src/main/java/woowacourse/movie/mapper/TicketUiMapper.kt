package woowacourse.movie.mapper

import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.Ticket
import woowacourse.movie.model.seat.Col
import woowacourse.movie.model.seat.Row
import woowacourse.movie.model.seat.Seat
import woowacourse.movie.model.seat.Seats
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
        seats = seats.values.joinToString(", ") { it.toSeatLabel() },
    )
}

fun TicketUiModel.toDomain(): Ticket {
    val selectedDate = formatStringDateDotSeparated(selectedDateText)
    val selectedTime = formatStringTimeWithMidnight24(selectedTimeText)

    val parsedSeats =
        if (seats.isBlank()) {
            emptyList()
        } else {
            seats.split(", ").map { seatName ->
                val rowChar = seatName.first()
                val colNumber = seatName.substring(1).toInt()

                val rowIndex = rowChar - 'A'
                val colIndex = colNumber - 1

                Seat(row = Row(rowIndex), col = Col(colIndex), isSelected = true)
            }
        }

    return Ticket(
        theater = theater,
        title = title,
        headCount = HeadCount(headCount),
        selectedDate = selectedDate,
        selectedTime = selectedTime,
        seats = Seats(parsedSeats),
    )
}
