package woowacourse.movie.mapper

import woowacourse.movie.data.Reservation
import woowacourse.movie.ui.model.TicketUiModel

fun Reservation.toUiModel(): TicketUiModel {
    return TicketUiModel(
        theater = theater,
        title = title,
        headCount = headCount,
        selectedDateText = selectedDate,
        selectedTimeText = selectedTime,
        totalPrice = price,
        seats = seats,
    )
}
