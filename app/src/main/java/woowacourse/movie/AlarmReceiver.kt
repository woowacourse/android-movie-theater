package woowacourse.movie

import android.Manifest
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.MainActivity.Companion.ALARM_CHANNEL_ID
import woowacourse.movie.booking.complete.BookingCompleteActivity
import woowacourse.movie.booking.complete.BookingType
import woowacourse.movie.ui.model.TicketUiModel

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        val ticket = intent?.getParcelableExtra<TicketUiModel>(KEY_NOTIFICATION_DATA)
        if (context != null && ticket != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
                ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS,
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                return
            }

            val message = context.getString(R.string.notification_sub_info, ticket.title)
            val title = context.getString(R.string.notification_content_title)

            val openIntent = BookingCompleteActivity.createIntent(context, BookingType.HISTORY, ticket)
            val contentIntent =
                PendingIntent.getActivity(
                    context,
                    0,
                    openIntent,
                    PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
                )

            val builder =
                NotificationCompat.Builder(context, ALARM_CHANNEL_ID)
                    .setSmallIcon(R.drawable.orang)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setContentIntent(contentIntent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)

            val manager =
                ContextCompat.getSystemService(
                    context,
                    NotificationManager::class.java,
                ) as NotificationManager
            manager.notify(1002, builder.build())
        }
    }

    companion object {
        private const val KEY_NOTIFICATION_DATA = "notificationData"

        fun createIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra(KEY_NOTIFICATION_DATA, ticket)
            }
        }
    }
}
