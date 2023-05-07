package woowacourse.movie.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.MovieBookingSeatInfoUIModel

class MovieReminder : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val canPush = getPushAlarmAllowed(context)
        if (!canPush) return

        val movieBookingSeatInfo =
            intent.getSerializableCompat<MovieBookingSeatInfoUIModel>(MOVIE_BOOKING_SEAT_INFO_KEY)
                ?: return
        val id = intent.getIntExtra(ALARM_NOTIFICATION_ID, 0)
        val notificationManager = MovieNotificationManager(context)
        notificationManager.createNotificationChannel()
        notificationManager.deliverNotification(movieBookingSeatInfo, id)
    }

    private fun getPushAlarmAllowed(context: Context) =
        SharedPreferenceUtil(context).getValue(
            SETTING_PUSH_ALARM_SWITCH_KEY,
            false
        )

    companion object {
        private const val MOVIE_BOOKING_SEAT_INFO_KEY = "movieBookingSeatInfo"
        private const val SETTING_PUSH_ALARM_SWITCH_KEY = "settingPushAlarmSwitchKey"
        private const val ALARM_NOTIFICATION_ID = "notificationId"
    }
}
