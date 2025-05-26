package woowacourse.movie.presentation

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.model.TicketBundleUiModel
import woowacourse.movie.presentation.view.history.detailResult.ReservationResultActivity
import woowacourse.movie.presentation.view.setting.PushPreferenceHelper

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val preferenceHelper = PushPreferenceHelper(context)
        if (!preferenceHelper.isPushEnabled()) {
            Log.d("test", "unEnabled")
            return
        }

        val ticket = intent.getParcelableExtra<TicketBundleUiModel>("ticket") ?: return

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("movie_alarm", "예매 알림", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        val activityIntent = Intent(context, ReservationResultActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("ticket",ticket)
        }

        val pendingIntent = PendingIntent.getActivity(
            context,
            ticket.title.hashCode(),
            activityIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, "movie_alarm")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("예매 알림")
            .setContentText("${ticket.title} 30분 후에 상영")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .build()

        notificationManager.notify(ticket.title.hashCode(), notification)
    }
}
