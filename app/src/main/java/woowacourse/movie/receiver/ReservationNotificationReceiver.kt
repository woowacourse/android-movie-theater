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
        val movieUiModel = receiveMovieViewModel(receivedIntent)
        val ticketsUiModel = receiveTicketsUiModel(receivedIntent)

        val notificationManager = getNotificationManager(context)
        val notificationBuilder = generateNotificationBuilder(notificationManager, context)

        val sendingIntent = generateSendingIntent(context, movieUiModel, ticketsUiModel)
        val pendingIntent = PendingIntent.getActivity(
            context, 123, sendingIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        setAlarmOption(notificationBuilder, movieUiModel, pendingIntent)
        val notification: Notification = notificationBuilder.build()
        if (isAlarmPossible(context)) notificationManager.notify(1, notification)
    }

    private fun receiveTicketsUiModel(intent: Intent): TicketsUiModel? {
        return intent.extras?.getSerializableCompat(TICKETS_KEY_VALUE)
    }

    private fun receiveMovieViewModel(intent: Intent): MovieUiModel? {
        return intent.extras?.getSerializableCompat(MOVIE_KEY_VALUE)
    }

    private fun getNotificationManager(context: Context): NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    private fun generateNotificationBuilder(
        notificationManager: NotificationManager,
        context: Context
    ): NotificationCompat.Builder {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationManager.createNotificationChannel(
                NotificationChannel(
                    CHANNEL_ID,
                    CHANNEL_NAME,
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            )
            NotificationCompat.Builder(context, CHANNEL_ID)
        } else {
            NotificationCompat.Builder(context)
        }
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

    private fun setAlarmOption(
        notificationBuilder: NotificationCompat.Builder,
        movieUiModel: MovieUiModel?,
        pendingIntent: PendingIntent
    ) {
        notificationBuilder.setContentTitle("예매 알림")
        notificationBuilder.setContentText(movieUiModel?.title + "  30분 후에 상영")
        notificationBuilder.setSmallIcon(R.drawable.ic_lock_idle_alarm)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.setContentIntent(pendingIntent)
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
