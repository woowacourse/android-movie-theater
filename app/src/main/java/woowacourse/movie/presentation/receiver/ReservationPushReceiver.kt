package woowacourse.movie.presentation.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.woowacourse.data.local.LocalDataStore
import woowacourse.movie.R
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.Companion.PUSH_ACTION
import woowacourse.movie.presentation.activities.main.alarm.PushAlarmManager.Companion.PUSH_DATA_KEY
import woowacourse.movie.presentation.activities.main.fragments.setting.contract.presenter.SettingPresenter.Companion.PUSH_ALLOW_KEY
import woowacourse.movie.presentation.extensions.getParcelableCompat
import woowacourse.movie.presentation.model.Reservation
import woowacourse.movie.presentation.notification.ReservationNotification

class ReservationPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != PUSH_ACTION) return
        if (isDeniedPush(context)) return

        val reservation: Reservation = intent.getParcelableCompat(PUSH_DATA_KEY) ?: return

        val reservationReminder = ReservationNotification(
            channelId = context.getString(R.string.reservation_reminder_channel_id),
            channelName = context.getString(R.string.reservation_reminder_channel_name),
            channelDesc = context.getString(R.string.reservation_reminder_channel_desc),
        )
        reservationReminder.notify(context, reservation)
    }

    private fun isDeniedPush(context: Context): Boolean {
        val preferences = LocalDataStore.getInstance(context)
        return !preferences.getBoolean(PUSH_ALLOW_KEY, true)
    }
}
