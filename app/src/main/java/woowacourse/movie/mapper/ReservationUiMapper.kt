package woowacourse.movie.mapper

import woowacourse.movie.data.Reservation
import woowacourse.movie.ui.model.TicketUiModel

fun Reservation.toUiModel(): TicketUiModel {
    return TicketUiModel(
        theater = this.theater,
        title = this.title,
        headCount = this.headCount,
        selectedDateText = this.selectedDate,
        selectedTimeText = this.selectedTime,
        totalPrice = this.price,
        seats = this.seats,
    )
}
