package woowacourse.movie.booking.complete.alarm

import woowacourse.movie.ui.model.TicketUiModel
import java.util.Calendar

interface AlarmTimeProvider {
    fun getAlarmTime(ticket: TicketUiModel): Calendar
}
