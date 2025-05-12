package woowacourse.movie.view.complete

import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.core.util.StringFormatter

data class CompleteScreenModel(
    val title: String,
    val bookingSchedule: String,
    val theaterName: String,
    val peopleCount: String,
    val seats: String,
    val price: String,
)

fun Ticket.toCompleteScreen(
    scheduleFormatter: String,
    seatFormatter: String,
    countFormatter: String,
    paymentFormatter: String,
): CompleteScreenModel {
    val dotFormatted = StringFormatter.dotDateFormat(bookingDate)
    val formatedSchedule = scheduleFormatter.format(dotFormatted, bookingTime)
    val formatedSeats =
        seats.joinToString {
            val rowLetter = ('A' + it.x.value - 1)
            val columnNumber = it.y.value
            seatFormatter.format(rowLetter, columnNumber)
        }
    val formatedCount = countFormatter.format(count.value)
    val formatedPrice = paymentFormatter.format(StringFormatter.thousandFormat(price))

    return CompleteScreenModel(
        title = title,
        bookingSchedule = formatedSchedule,
        theaterName = theaterName,
        peopleCount = formatedCount,
        seats = formatedSeats,
        price = formatedPrice,
    )
}
