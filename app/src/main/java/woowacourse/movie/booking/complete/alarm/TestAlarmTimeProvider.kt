package woowacourse.movie.booking.complete.alarm

import woowacourse.movie.ui.model.TicketUiModel
import java.util.Calendar
import java.util.TimeZone

class TestAlarmTimeProvider : AlarmTimeProvider {
    override fun getAlarmTime(ticket: TicketUiModel): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).apply {
            add(Calendar.SECOND, 15)
            set(Calendar.MILLISECOND, 0)
        }
    }
}
