package woowacourse.movie.booking.complete

import woowacourse.movie.data.SettingPreference
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.TicketUiModel
import java.time.LocalDateTime
import java.time.ZoneId

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val settingPreference: SettingPreference,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: Ticket

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket.toDomain()

        view.showBookingCompleteResult(ticket)

        if (settingPreference.isAlarmPermitted()) {
            view.makeAlarm(ticket, calculateMovieAlarmTime())
        }
    }

    private fun calculateMovieAlarmTime(): Long {
        val dateTime = LocalDateTime.of(ticket.selectedDate, ticket.selectedTime)
        return dateTime
            .minusMinutes(30)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .toEpochMilli()
    }
}
