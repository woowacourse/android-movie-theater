package woowacourse.movie.presentation.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.notification.NotificationPreference
import woowacourse.movie.data.notification.NotificationPreferenceImpl
import woowacourse.movie.domain.model.movie.MovieTicket
import woowacourse.movie.presentation.notification.NotificationScheduler.Companion.KEY_TICKET
import woowacourse.movie.presentation.notification.NotificationScheduler.Companion.KEY_TIME
import woowacourse.movie.presentation.util.getSerializableExtraCompat

class TicketNotificationReceiver(
    private val notificationPreference: NotificationPreference = NotificationPreferenceImpl(),
) : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!notificationPreference.isNotificationEnabled()) return
        val ticket = intent.getSerializableExtraCompat(KEY_TICKET, MovieTicket::class.java) ?: return
        val time = intent.getLongExtra(KEY_TIME, 0)
        NotificationHelper(context).showMovieTicketReminder(ticket, time)
    }
}
