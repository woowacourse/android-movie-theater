package woowacourse.movie.booking.complete

import woowacourse.movie.ui.model.TicketUiModel

interface AlarmScheduler {
    fun scheduleAlarm(
        bookingType: String,
        ticket: TicketUiModel,
    )
}
