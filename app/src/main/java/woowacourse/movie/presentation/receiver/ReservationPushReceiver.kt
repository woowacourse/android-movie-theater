package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.presentation.MovieApplication
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.Companion.PUSH_ACTION
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.Companion.PUSH_DATA_KEY
import woowacourse.movie.presentation.activities.main.fragments.setting.SettingFragment
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.reminder.ReservationReminder

class ReservationPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != PUSH_ACTION) return
        if (isDeniedPush()) return

        val reservation: Reservation = intent.getParcelableCompat(PUSH_DATA_KEY) ?: return

        val reservationReminder = ReservationReminder(
            channelId = context.getString(R.string.reservation_reminder_channel_id),
            channelName = context.getString(R.string.reservation_reminder_channel_name),
            channelDesc = context.getString(R.string.reservation_reminder_channel_desc),
        )
        reservationReminder.notify(context, reservation)
    }

    private fun isDeniedPush(): Boolean {
        val preferences = MovieApplication.dataStore
        return !preferences.getBoolean(SettingFragment.PUSH_ALLOW_KEY, true)
    }
}
