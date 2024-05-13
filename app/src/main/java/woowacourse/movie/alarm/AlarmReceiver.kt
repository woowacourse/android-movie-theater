package woowacourse.movie.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == "movie.movie_start_after_30min") {
            val movieStartNotificationManager =
                MovieStartNotificationManager(
                    context,
                    intent,
                )
            movieStartNotificationManager.startNotification()
        }
    }
}
