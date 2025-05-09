package woowacourse.movie.feature.receiver

import android.Manifest
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import woowacourse.movie.MovieApplication
import woowacourse.movie.R

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        context ?: return
        intent?.action ?: return

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }

        val movieTitle = intent.getStringExtra(MOVIE_NAME_KEY) ?: ""
        val notification =
            NotificationCompat
                .Builder(context, MovieApplication.MOVIE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_dino_blue)
                .setContentTitle(context.getString(R.string.booking_history_notification_title))
                .setContentText(context.getString(R.string.booking_history_notification_description, movieTitle))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()

        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(movieTitle.hashCode(), notification)
    }

    companion object {
        const val MOVIE_NAME_KEY = "MOVIE_NAME"
    }
}
