package woowacourse.movie.feature.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.feature.model.BookingInfoUiModel
import woowacourse.movie.feature.setting.NotificationHelper.showNotification

class MyReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        if (intent?.action == Intent.ACTION_SCREEN_ON) {
            val alarmSetting = context.getSharedPreferences("alarmSetting", Context.MODE_PRIVATE)
            if (!alarmSetting.getBoolean("NOTIFICATION_ENABLED", true)) return

            val bookingInfo: BookingInfoUiModel? = intent.getParcelableExtra("BOOKING_INFO")

            if (bookingInfo != null) showNotification(context, bookingInfo)
        }
    }
}
