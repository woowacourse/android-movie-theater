package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.woowacourse.data.local.PreferenceManager
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.PUSH_DATA_KEY
import woowacourse.movie.presentation.activities.main.fragments.setting.AlarmRepository.Companion.KEY_ALLOW
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.item.Reservation
import woowacourse.movie.presentation.reminder.ReservationReminder

class ReservationPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (isDeniedPush(context)) return

        val reservation: Reservation = intent.getParcelableCompat(PUSH_DATA_KEY) ?: return

        val reservationReminder = ReservationReminder(
            channelId = context.getString(R.string.reservation_reminder_channel_id),
            channelName = context.getString(R.string.reservation_reminder_channel_name),
            channelDesc = context.getString(R.string.reservation_reminder_channel_desc),
        )
        reservationReminder.notify(context, reservation)
    }

    private fun isDeniedPush(context: Context): Boolean {
        val preferences = PreferenceManager.getInstance(context)
        return !preferences.getBoolean(KEY_ALLOW, true)
    }
}
