package woowacourse.movie.view.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import woowacourse.movie.model.ticket.MovieTicket
import woowacourse.movie.presenter.setting.SettingPresenter.Companion.PUSH_ENABLED_DATA_KEY
import woowacourse.movie.view.reservationComplete.ReservationCompleteActivity.Companion.TICKET_DATA_KEY
import woowacourse.movie.view.setting.SettingFragment.Companion.SETTING_DATA_KEY
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmFactory(
    private val context: Context,
    private val requestAlarmPermissionScreen: () -> Unit,
) {
    fun scheduleNotification(movieTicket: MovieTicket) {
        if (!canScheduleNotification()) return
        val triggerTimeMillis = getTriggerTimeMillis(movieTicket)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
            val pendingIntent = createPendingIntent(movieTicket)

            scheduleExactAlarm(alarmManager, triggerTimeMillis, pendingIntent)
        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun scheduleExactAlarm(
        alarmManager: AlarmManager,
        triggerTimeMillis: Long,
        pendingIntent: PendingIntent,
    ) {
        if (alarmManager.canScheduleExactAlarms()) {
            setAlarmTimeMillis(alarmManager, triggerTimeMillis, pendingIntent)
        } else {
            requestAlarmPermissionScreen()
        }
    }

    private fun setAlarmTimeMillis(
        alarmManager: AlarmManager,
        triggerTimeMillis: Long,
        pendingIntent: PendingIntent,
    ) {
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerTimeMillis,
            pendingIntent,
        )
    }

    private fun canScheduleNotification(): Boolean {
        val prefs = context.getSharedPreferences(SETTING_DATA_KEY, Context.MODE_PRIVATE)
        return prefs.getBoolean(PUSH_ENABLED_DATA_KEY, false)
    }

    private fun getTriggerTimeMillis(ticket: MovieTicket): Long {
        val triggerTime = ticket.selectedTime.value.minusMinutes(30)
        val triggerDateTime = LocalDateTime.of(ticket.selectedDate, triggerTime)
        val zoneId = ZoneId.of(SEOUL_ZONE_ID)
        return triggerDateTime.atZone(zoneId).toInstant().toEpochMilli()
    }

    private fun createPendingIntent(movieTicket: MovieTicket): PendingIntent {
        val intent =
            Intent(context, AlarmReceiver::class.java).apply {
                action = MOVIE_ALARM_ACTION
                putExtra(TICKET_DATA_KEY, movieTicket)
            }
        return PendingIntent.getBroadcast(
            context,
            movieTicket.title.hashCode(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        private const val SEOUL_ZONE_ID = "Asia/Seoul"
        const val MOVIE_ALARM_ACTION = "movie_alarm"
    }
}
