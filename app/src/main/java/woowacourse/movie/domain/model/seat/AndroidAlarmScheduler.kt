package woowacourse.movie.domain.model.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.os.SystemClock
import woowacourse.movie.domain.model.theater.BookedTicket
import woowacourse.movie.ui.seat.view.AlarmReceiver

class AndroidAlarmScheduler(
    private val context: Context,
) : AlarmScheduler {
    override fun scheduleBookingAlarm(bookedTicket: BookedTicket) {
        val alarmMgr = context.getSystemService(ALARM_SERVICE) as AlarmManager
        val intent = AlarmReceiver.newIntent(context, bookedTicket)
        val alarmIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        alarmMgr.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            SystemClock.elapsedRealtime() + 3000,
            // 테스트를 위해 3초로 설정
            alarmIntent,
        )
    }
}
