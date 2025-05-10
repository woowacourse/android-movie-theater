package woowacourse.movie.booking.complete

import woowacourse.movie.ui.model.TicketUiModel
import woowacourse.movie.util.Formatter.formatStringDate
import woowacourse.movie.util.Formatter.formatStringTimeWithMidnight24
import java.util.Calendar
import java.util.TimeZone

class DefaultAlarmTimeProvider : AlarmTimeProvider {
    override fun getAlarmTime(ticket: TicketUiModel): Calendar {
        val time = formatStringTimeWithMidnight24(ticket.selectedTimeText).minusMinutes(30)
        val date = formatStringDate(ticket.selectedDateText)

        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).apply {
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.monthValue - 1)
            set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
    }
}
