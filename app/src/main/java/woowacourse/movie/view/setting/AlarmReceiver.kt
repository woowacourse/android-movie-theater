package woowacourse.movie.view.setting

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
        context: Context?,
        intent: Intent?,
    ) {
        if (context == null || intent == null) return
        if (intent.action == "movie_alarm") {
            val movieTitle =
                intent.getStringExtra("movie_title") ?: throw IllegalArgumentException()

            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                val channel =
                    NotificationChannel(
                        "movie_alarm",
                        "Movie Alarms",
                        NotificationManager.IMPORTANCE_HIGH,
                    )
                notificationManager.createNotificationChannel(channel)

                val notification =
                    NotificationCompat
                        .Builder(context, "movie_alarm")
                        .setSmallIcon(R.drawable.ic_android_green_24dp)
                        .setContentTitle("Movie")
                        .setContentText("$movieTitle 30분 후에 상영")
                        .build()

                notificationManager.notify(movieTitle.hashCode(), notification)
            }
        }
    }
}
