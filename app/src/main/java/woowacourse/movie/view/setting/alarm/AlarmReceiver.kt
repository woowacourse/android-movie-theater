package woowacourse.movie.view.setting.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(android.Manifest.permission.POST_NOTIFICATIONS)
            != android.content.pm.PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel =
                NotificationChannel(
                    "alarm_channel",
                    "예매 알림",
                    NotificationManager.IMPORTANCE_HIGH,
                ).apply {
                    description = "예매 알림 채널입니다."
                }

            notificationManager.createNotificationChannel(channel)
        }

        val movieTitle = intent.getStringExtra("MOVIE_TITLE")

        val builder =
            NotificationCompat.Builder(context, "alarm_channel")
                .setSmallIcon(R.drawable.alarm_icon)
                .setContentTitle("예매 알림")
                .setContentText("$movieTitle 30분 후에 상영")

        notificationManager.notify(1, builder.build())
    }
}
