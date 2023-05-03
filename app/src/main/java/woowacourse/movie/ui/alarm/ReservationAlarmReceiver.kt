package woowacourse.movie.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.setting.SettingFragment
import woowacourse.movie.utils.getSerializableExtraCompat

class ReservationAlarmReceiver : BroadcastReceiver() {
    private lateinit var _context: Context
    private val notificationOnChannel by lazy { NotificationOnChannel(_context) }
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        _context = context

        val movie: MovieTicketModel =
            intent.getSerializableExtraCompat(SettingFragment.KEY_MOVIE)
                ?: return
        notificationOnChannel.createNotificationChannel()
        notificationOnChannel.notify(_context, movie)
    }
}
