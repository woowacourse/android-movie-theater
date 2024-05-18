package woowacourse.movie.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.sharedpreference.MovieSharedPreferences

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (intent.action == context.getString(R.string.notification_action)) {
            val prefs = MovieSharedPreferences(context)
            if (prefs.getNotificationPreference()) {
                val movieStartNotificationManager =
                    MovieStartNotificationManager(
                        context,
                        intent,
                    )
                movieStartNotificationManager.startNotification()
            }
        }
    }
}
