package woowacourse.movie.booking.complete

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.AlarmReceiver
import woowacourse.movie.data.ReservationDao
import woowacourse.movie.mapper.toEntity
import woowacourse.movie.ui.model.TicketUiModel
import woowacourse.movie.util.Formatter.formatStringDate
import woowacourse.movie.util.Formatter.formatStringTimeWithMidnight24
import java.time.LocalDate
import java.time.LocalTime
import java.util.Calendar
import java.util.TimeZone
import kotlin.concurrent.thread

class BookingCompletePresenter(
    private val view: BookingCompleteContract.View,
    private val reservationDao: ReservationDao,
) : BookingCompleteContract.Presenter {
    private lateinit var ticket: TicketUiModel

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket
        view.showBookingCompleteResult(ticket)
    }

    override fun saveReservation(
        ticket: TicketUiModel,
        type: String,
    ) {
        thread {
            if (type == BookingType.RESERVATION.name) {
                reservationDao.insertReservation(ticket.toEntity())
            }
        }
    }

    @SuppressLint("ScheduleExactAlarm")
    override fun setNotification(
        context: Context,
        bookingType: String,
        ticket: TicketUiModel,
    ) {
        if (bookingType == BookingType.RESERVATION.name) {
            val formattedTime = formatStringTimeWithMidnight24(ticket.selectedTimeText)
            val formattedDate = formatStringDate(ticket.selectedDateText)

            val alarmTime = formattedTime.minusMinutes(30)
            val alarmDate = formattedDate

            val calendar = setAlarmCalendar(alarmTime, alarmDate)
            val intent = AlarmReceiver.createIntent(context, ticket)

            val pendingIntent =
                PendingIntent.getBroadcast(
                    context,
                    0,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
                )

            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                calendar.timeInMillis,
                pendingIntent,
            )
        }
    }

    private fun setAlarmCalendar(
        time: LocalTime,
        date: LocalDate,
    ): Calendar {
        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.YEAR, date.year)
            set(Calendar.MONTH, date.monthValue - 1)
            set(Calendar.DAY_OF_MONTH, date.dayOfMonth)
            set(Calendar.HOUR_OF_DAY, time.hour)
            set(Calendar.MINUTE, time.minute)
            set(Calendar.SECOND, 0)
        }
    }

//    private fun setAlarmCalendarForTest(): Calendar {
//        return Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul")).apply {
//            add(Calendar.SECOND, 15)
//            set(Calendar.MILLISECOND, 0)
//        }
//    }
}
