package woowacourse.movie.ui.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.notification.PendingIntentGenerator
import java.util.TimeZone

class ScreeningTimeReminder(
    private val context: Context,
) : TimeReminder {

    override fun remind(reservation: ReservationUiModel) {
        val screeningDateTime =
            reservation.bookedDateTime
                .minusMinutes(30)
                .atZone(TimeZone.getDefault().toZoneId())
                .toInstant()
                .toEpochMilli()

        createAlarmManager().set(
            AlarmManager.RTC_WAKEUP,
            screeningDateTime,
            createPendingIntent(reservation),
        )
    }

    private fun createAlarmManager(): AlarmManager {

        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    private fun createPendingIntent(reservationUiModel: ReservationUiModel): PendingIntent {

        return PendingIntentGenerator.generateBroadcastingIntent(
            intent = AlarmReceiver.getIntent(context, reservationUiModel),
            context = context
        )
    }
}
