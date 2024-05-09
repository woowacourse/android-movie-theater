package woowacourse.movie.receiver

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.TheaterApplication.Companion.CHANNEL_ID
import woowacourse.movie.presentation.ui.main.setting.SettingFragment
import woowacourse.movie.presentation.ui.reservation.ReservationActivity
import woowacourse.movie.presentation.ui.reservation.ReservationActivity.Companion.PUT_EXTRA_KEY_RESERVATION_ID

class MyBroadCastReceiver : BroadcastReceiver() {
    private val actionName: String = ACTION_NAME

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val pref =
            context.getSharedPreferences("ReservationNotificationSettings", Context.MODE_PRIVATE)
        val isNotifiable = pref.getBoolean(SettingFragment.PREF_KEY_RESERVATION_NOTIFICATION, false)
        if (actionName == intent.action && isNotifiable) {
            notifyReservationAlarm(context, intent)
        }
    }

    private fun notifyReservationAlarm(
        context: Context,
        intent: Intent,
    ) {
        with(intent) {
            val reservationId = getLongExtra(PUT_EXTRA_KEY_RESERVATION_ID, DEFAULT_VALUE)
            val movieTitle = getStringExtra(PUT_EXTRA_KEY_MOVIE_TITLE)
            val activityIntent = getReservationActivityIntent(context, movieTitle, reservationId)
            val notification = createNotification(context, movieTitle, activityIntent)
            context.getSystemService(NotificationManager::class.java)
                .notify(reservationId.toInt(), notification)
        }
    }

    private fun getReservationActivityIntent(
        context: Context,
        movieTitle: String?,
        reservationId: Long,
    ): Intent =
        Intent(context, ReservationActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra(PUT_EXTRA_KEY_MOVIE_TITLE, movieTitle)
            putExtra(PUT_EXTRA_KEY_RESERVATION_ID, reservationId)
        }

    private fun createNotification(
        context: Context,
        movieTitle: String?,
        activityIntent: Intent,
    ): Notification {
        return NotificationCompat.Builder(context, CHANNEL_ID)
            .setContentTitle(CONTENT_TITLE)
            .setContentText(CONTENT_TEXT.format(movieTitle))
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(getPendingIntent(context, activityIntent))
            .setAutoCancel(true)
            .build()
    }

    private fun getPendingIntent(
        context: Context,
        activityIntent: Intent,
    ) = PendingIntent.getActivity(
        context,
        RESERVATION_PENDING_INTENT_CODE,
        activityIntent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE,
    )

    companion object {
        const val ACTION_NAME = "RESERVATION_ALARM_ACTION"
        const val CONTENT_TITLE = "예매 알림"
        const val CONTENT_TEXT = "%s 30분 후에 상영"
        const val PUT_EXTRA_KEY_MOVIE_TITLE = "movieTitle"
        const val PUT_EXTRA_KEY_ACTIVITY = "Activity"
        const val DEFAULT_VALUE = -1L
        const val RESERVATION_PENDING_INTENT_CODE = 0
    }
}
