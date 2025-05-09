package woowacourse.movie.feature.receiver

import android.Manifest
import android.app.Notification
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
import woowacourse.movie.feature.setting.view.SettingFragment.Companion.NOTIFICATION_SETTING_KEY

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context?,
        intent: Intent?,
    ) {
        when {
            context == null -> return
            intent?.action == null -> return
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> return
            context.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED -> return
            getIsNotificationEnabled(context) == false -> return
        }

        val movieTitle = intent.getStringExtra(MOVIE_NAME_KEY) ?: ""
        val notification = createNotification(context, movieTitle)

        val notificationManager = ContextCompat.getSystemService(context, NotificationManager::class.java)
        notificationManager?.notify(movieTitle.hashCode(), notification)
    }

    private fun getIsNotificationEnabled(context: Context?): Boolean? {
        val sharedPreference = context?.getSharedPreferences(NOTIFICATION_SETTING_KEY, Context.MODE_PRIVATE)
        return sharedPreference?.getBoolean(NOTIFICATION_SETTING_KEY, true)
    }

    private fun createNotification(
        context: Context,
        movieTitle: String,
    ): Notification {
        val notification =
            NotificationCompat
                .Builder(context, MovieApplication.MOVIE_NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_dino_blue)
                .setContentTitle(context.getString(R.string.booking_history_notification_title))
                .setContentText(context.getString(R.string.booking_history_notification_description, movieTitle))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .build()
        return notification
    }

    companion object {
        const val MOVIE_NAME_KEY = "MOVIE_NAME"
    }
}
