package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.movies.Movie
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.StringFormatter
import woowacourse.movie.view.bindingadapter.ImageSource
import woowacourse.movie.view.uiModel.MovieUiModel
import woowacourse.movie.view.uiModel.TicketUiModel

fun Movie.toUiModel(): MovieUiModel {
    return MovieUiModel(
        title = title,
        posterResource = ImageSource.Resource(posterResource),
        screeningStartDate = StringFormatter.dotDateFormat(screeningStartDate),
        screeningEndDate = StringFormatter.dotDateFormat(screeningEndDate),
        runningTime = runningTime,
    )
}

fun Ticket.toUiModel(
    scheduleFormatter: String,
    seatFormatter: String,
    countFormatter: String,
    paymentFormatter: String,
): TicketUiModel {
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

    return TicketUiModel(
        title = title,
        bookingSchedule = formatedSchedule,
        theaterName = theaterName,
        peopleCount = formatedCount,
        seats = formatedSeats,
        price = formatedPrice,
    )
}
