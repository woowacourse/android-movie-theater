package woowacourse.movie.ui.seat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.ui.PendingIntentGenerator
import java.time.LocalDateTime
import java.util.TimeZone

class ScreeningTimeReminder(
    context: Context,
    reservationUiModel: ReservationUiModel,
) {
    private val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent: PendingIntent =
        PendingIntentGenerator.generateBroadcastingIntent(
            intent = AlarmReceiver.getIntent(context, reservationUiModel),
            context = context
        )

    init {
        // val screeningDateTime =
        //     reservationUiModel.bookedDateTime
        //         .minusMinutes(30)
        //         .atZone(TimeZone.getDefault().toZoneId())
        //         .toInstant()
        //         .toEpochMilli()

        val screeningDateTime =
            LocalDateTime.now().plusSeconds(3).atZone(TimeZone.getDefault().toZoneId())
                .toInstant().toEpochMilli()

        alarmManager.set(
            AlarmManager.RTC_WAKEUP,
            screeningDateTime,
            intent,
        )
    }
}
