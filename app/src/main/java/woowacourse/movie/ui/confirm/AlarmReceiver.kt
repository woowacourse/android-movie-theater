package woowacourse.movie.ui.confirm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.util.PreferenceUtil
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.fragment.setting.SettingFragment.Companion.NOTIFICATIONS
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.sendNotification

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val tickets = intent?.extras?.getParcelableCompat<TicketsState>("a")

        val sharedPreference = PreferenceUtil(context)
        val isNotification = sharedPreference.getBoolean(NOTIFICATIONS, false)
        Log.d("mendel", "수신옴: 수신여부 $isNotification")
        if (isNotification) {
            tickets?.let {
                context.sendNotification(
                    R.drawable.ic_launcher_foreground,
                    context.getString(R.string.alarm_receiver_notification_title),
                    context.getString(R.string.alarm_receiver_notification_text)
                        .format(it.movieState.title),
                    true
                )
            }
        }
    }
}
