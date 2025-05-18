package woowacourse.movie.feature.bookingseat

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.widget.Toast
import woowacourse.movie.R
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.setting.MyReceiver
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler(
    private val context: Context,
) {
    fun scheduleAlarm(bookingInfo: BookingInfoUiModel) {
        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager

        val localDateTime =
            LocalDateTime.of(
                bookingInfo.date.year,
                bookingInfo.date.month,
                bookingInfo.date.day,
                bookingInfo.movieTime.hour,
                bookingInfo.movieTime.minute,
            )
        val alarmTime = localDateTime.minusMinutes(30).atZone(ZoneId.systemDefault())

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S &&
            !alarmManager.canScheduleExactAlarms()
        ) {
            Toast
                .makeText(
                    context,
                    context.getString(R.string.alarm_setting_error_message),
                    Toast.LENGTH_LONG,
                ).show()
            return
        }

        val intent =
            Intent(context, MyReceiver::class.java).apply {
                putExtra("BOOKING_INFO", bookingInfo)
            }

        val pendingIntent =
            PendingIntent.getBroadcast(
                context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime.toEpochSecond(),
//            System.currentTimeMillis(),
            pendingIntent,
        )
    }
}
