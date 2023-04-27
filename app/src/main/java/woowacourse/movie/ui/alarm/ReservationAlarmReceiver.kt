package woowacourse.movie.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.setting.SettingFragment
import woowacourse.movie.utils.getSerializableExtraCompat

class ReservationAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationManager = NotificationCreator(context)
        val movie: MovieTicketModel =
            intent.getSerializableExtraCompat(SettingFragment.KEY_MOVIE)
                ?: return

        notificationManager.createNotificationChannel()
        notificationManager.makeNotification(context, movie)
    }
}
