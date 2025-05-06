package woowacourse.movie.view.mapper

import woowacourse.movie.domain.model.Movie
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.view.core.util.StringFormatter
import woowacourse.movie.view.core.bindingadapter.ImageSource
import woowacourse.movie.view.uiModel.MovieUiModel
import woowacourse.movie.view.uiModel.TicketUiModel

fun Movie.toUiModel(
    datePeriodFormatter: String,
    runningTimeFormatter: String,
): MovieUiModel {
    val startDate = StringFormatter.dotDateFormat(screeningStartDate)
    val endDate = StringFormatter.dotDateFormat(screeningEndDate)

    val screeningPeriod = datePeriodFormatter.format(startDate, endDate)
    val formattedRunningTimme = runningTimeFormatter.format(runningTime)

    return MovieUiModel(
        title = title,
        posterResource = ImageSource.Resource(posterResource),
        screeningPeriod = screeningPeriod,
        runningTime = formattedRunningTimme,
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
