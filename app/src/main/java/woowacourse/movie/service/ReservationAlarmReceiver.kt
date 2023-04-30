package woowacourse.movie.service

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.activity.SeatSelectionActivity
import woowacourse.movie.fragment.SettingFragment
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.error.BroadcastReceiverError.returnWithError
import woowacourse.movie.view.error.ViewError
import woowacourse.movie.view.getSerializable
import woowacourse.movie.view.setting.Setting
import woowacourse.movie.view.setting.SharedSetting

class ReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val setting: Setting = SharedSetting(context)
        if (intent.action == SeatSelectionActivity.ACTION_ALARM && setting.getValue(
                SettingFragment.SETTING_NOTIFICATION
            )
        ) {
            val reservation =
                intent.extras?.getSerializable<ReservationViewData>(ReservationViewData.RESERVATION_EXTRA_NAME)
                    ?: return returnWithError(ViewError.MissingExtras(ReservationViewData.RESERVATION_EXTRA_NAME))
            Log.d("HKHK", reservation.toString())
            val pendingIntent = Notification.makeNotificationPendingIntent(context, reservation)
            val builder = Notification.makeNotificationBuilder(context, reservation, pendingIntent)
            Notification.notifyNotification(context, builder)
        }
    }

    companion object {
        val requestCode: Int
            get() = System.currentTimeMillis().toInt()

        fun from(context: Context, reservation: ReservationViewData): PendingIntent {
            val intent: Intent = Intent(SeatSelectionActivity.ACTION_ALARM).putExtra(
                ReservationViewData.RESERVATION_EXTRA_NAME, reservation
            )
            return PendingIntent.getBroadcast(
                context,
                requestCode,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}
