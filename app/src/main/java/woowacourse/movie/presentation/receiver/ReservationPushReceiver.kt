package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.woowacourse.data.local.Preferences
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.Companion.PUSH_ACTION
import woowacourse.movie.presentation.activities.main.fragments.setting.SettingFragment
import woowacourse.movie.presentation.activities.ticketingresult.TicketingResultActivity
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.reminder.ReservationReminder

class ReservationPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != PUSH_ACTION) return
        if (isDeniedPush(context)) return

        val reservation: Reservation =
            intent.getParcelableCompat(TicketingResultActivity.RESERVATION_KEY) ?: return

        val reservationReminder = ReservationReminder(
            channelId = context.getString(R.string.reservation_reminder_channel_id),
            channelName = context.getString(R.string.reservation_reminder_channel_name),
            channelDesc = context.getString(R.string.reservation_reminder_channel_desc),
        )
        reservationReminder.notify(context, reservation)
    }

    private fun isDeniedPush(context: Context): Boolean {
        val preferences = Preferences(context)
        return !preferences.getBoolean(SettingFragment.PUSH_ALLOW_KEY, true)
    }
}
