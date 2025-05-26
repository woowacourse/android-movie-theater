package woowacourse.movie.presentation

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.view.setting.PushPreferenceHelper
import woowacourse.movie.presentation.util.NotificationUtil

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val preferenceHelper = PushPreferenceHelper(context)
        if (!preferenceHelper.isPushEnabled()) {
            Log.d("test", "unEnabled")
            return
        }

        val ticketBundle = intent.getParcelableExtra<TicketBundleUiModel>(TICKET_BUNDLE_KEY) ?: return


        NotificationUtil.ensureNotificationChannel(context)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val activityIntent = createAlarmIntent(context , ticketBundle)

        val pendingIntent = PendingIntent.getActivity(
            context,
            ticketBundle.title.hashCode(),
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "movie_alarm")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(RESERVATION_TITLE_MESSAGE)
            .setContentText(ticketBundle.title+RESERVATION_MESSAGE)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(ticketBundle.title.hashCode(), notification)
    }

    companion object {
        private const val RESERVATION_MESSAGE = " 30분 후에 상영"
        private const val RESERVATION_TITLE_MESSAGE = "예매 알림"
        private const val TICKET_BUNDLE_KEY = "ticket"
        fun createAlarmIntent(
            context: Context,
            ticketBundle: TicketBundleUiModel,
        ): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra(TICKET_BUNDLE_KEY, ticketBundle)
            }
        }
    }
}
