package woowacourse

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.AlarmReceiver
import woowacourse.movie.model.Alarm
import woowacourse.movie.setting.AlarmInterface
import woowacourse.movie.usecase.FetchAllReservationsUseCase
import woowacourse.movie.util.runOnOtherThreadAndReturn

class MovieAlarmManager(
    private val context: Context,
    private val fetchAllReservationsUseCase: FetchAllReservationsUseCase,
) : AlarmInterface {
    private val alarmMgr = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    override fun makeAlarms() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU && !alarmMgr.canScheduleExactAlarms()) return
        getAlarmsFromReservations().onSuccess { alarms ->
            alarms.forEach {
                val intent = AlarmReceiver.getIntent(context, it.id, it.title)
                val requestCode = it.id.toInt()
                val pendingIntent = getPendingIntent(requestCode, intent)
                val alarmClock = AlarmManager.AlarmClockInfo(it.alarmTime, pendingIntent)
                alarmMgr.setAlarmClock(alarmClock, pendingIntent)
            }
        }
    }

    override fun deleteAllAlarms() {
        getAlarmsFromReservations().onSuccess { alarms ->
            alarms.forEach {
                val intent = Intent(context, AlarmReceiver::class.java)
                val requestCode = it.id.toInt()
                val alarmIntent = getPendingIntent(requestCode, intent)
                alarmMgr.cancel(alarmIntent)
            }
        }
    }

    private fun getAlarmsFromReservations() =
        runOnOtherThreadAndReturn {
            fetchAllReservationsUseCase().map { reservations ->
                reservations.map {
                    Alarm(it, 30)
                }
            }
        }

    private fun getPendingIntent(
        requestCode: Int,
        intent: Intent,
    ): PendingIntent =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
            )
        } else {
            PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT,
            )
        }
}
