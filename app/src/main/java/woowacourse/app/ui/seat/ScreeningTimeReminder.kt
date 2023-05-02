package woowacourse.app.ui.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import woowacourse.app.model.ReservationUiModel
import java.time.LocalDateTime
import java.util.TimeZone

class ScreeningTimeReminder(
    context: Context,
    reservationUiModel: ReservationUiModel,
) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val alarmIntent: PendingIntent =
        AlarmReceiver.getIntent(context, reservationUiModel).let { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(
                    context,
                    reservationUiModel.id.toInt(),
                    intent,
                    PendingIntent.FLAG_IMMUTABLE,
                )
            } else {
                PendingIntent.getBroadcast(
                    context,
                    reservationUiModel.id.toInt(),
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT,
                )
            }
        }

    init {
        val screeningDateTime =
            reservationUiModel.bookedDateTime
                .minusMinutes(reservationUiModel.alarmCycle)
                .atZone(TimeZone.getDefault().toZoneId())
                .toInstant()
                .toEpochMilli()

        val testTime = LocalDateTime.now()
            .plusSeconds(10)
            .atZone(TimeZone.getDefault().toZoneId())
            .toInstant()
            .toEpochMilli()

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            testTime,
            alarmIntent,
        )
    }
}
