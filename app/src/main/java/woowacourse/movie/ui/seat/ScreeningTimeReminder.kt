package woowacourse.movie.ui.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.os.Build
import woowacourse.movie.model.ReservationUiModel
import java.time.LocalDateTime
import java.util.TimeZone

class ScreeningTimeReminder(
    private val context: Context,
    reservationUiModel: ReservationUiModel,
) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    private val alarmIntent: PendingIntent =
        AlarmReceiver.getIntent(context, reservationUiModel).let { intent ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
            } else {
                PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            }
        }

    init {
        val screeningDateTime =
            reservationUiModel.bookedDateTime
                .atZone(TimeZone.getDefault().toZoneId())
                .toInstant()
                .toEpochMilli()

        val test = LocalDateTime.now().plusSeconds(10).atZone(TimeZone.getDefault().toZoneId())
            .toInstant().toEpochMilli()

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            test,
            alarmIntent,
        )
    }
}
