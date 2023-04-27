package woowacourse.movie.receiver

import android.R
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TicketsUiModel

class ReservationNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, receivedIntent: Intent) {
        val reservationNotificationHelper = NotificationHelper(context, CHANNEL_ID, CHANNEL_NAME)
        val movieUiModel = receiveMovieViewModel(receivedIntent)
        val ticketsUiModel = receiveTicketsUiModel(receivedIntent)
        val notificationManager = reservationNotificationHelper.generateNotificationManger()
        val notificationBuilder =
            reservationNotificationHelper.generateNotificationBuilder(notificationManager)

        val sendingIntent = generateSendingIntent(context, movieUiModel, ticketsUiModel)
        val pendingIntent = PendingIntent.getActivity(
            context, 123, sendingIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        reservationNotificationHelper.bindNotification(
            notificationBuilder = notificationBuilder,
            title = "예매 알림",
            contextText = "${movieUiModel?.title} 30분 후에 상영",
            smallIcon = R.drawable.ic_lock_idle_alarm,
            isAutoCancel = true,
            pendingIntent = pendingIntent
        )
        val notification: Notification = reservationNotificationHelper.generateNotification(notificationBuilder)
        if (isAlarmPossible(context)) notificationManager.notify(1, notification)
    }

    private fun receiveTicketsUiModel(intent: Intent): TicketsUiModel? {
        return intent.extras?.getSerializableCompat(TICKETS_KEY_VALUE)
    }

    private fun receiveMovieViewModel(intent: Intent): MovieUiModel? {
        return intent.extras?.getSerializableCompat(MOVIE_KEY_VALUE)
    }

    private fun generateSendingIntent(
        context: Context,
        movieUiModel: MovieUiModel?,
        ticketsUiModel: TicketsUiModel?
    ): Intent {
        val sendingIntent = Intent(context, ReservationResultActivity::class.java)
        sendingIntent.putExtra(MOVIE_KEY_VALUE, movieUiModel)
        sendingIntent.putExtra(TICKETS_KEY_VALUE, ticketsUiModel)
        return sendingIntent
    }
    private fun isAlarmPossible(context: Context): Boolean {
        val sharedPreferences = context.getSharedPreferences(
            SETTING,
            AppCompatActivity.MODE_PRIVATE
        )
        return sharedPreferences.getBoolean(PUSH_ALARM_KEY, false)
    }

    companion object {
        private const val CHANNEL_ID = "channel1"
        private const val CHANNEL_NAME = "Channel1"
        private const val MOVIE_KEY_VALUE = "movie"
        private const val TICKETS_KEY_VALUE = "tickets"
        private const val SETTING = "settings"
        private const val PUSH_ALARM_KEY = "pushAlarm"
    }
}
