package woowacourse.movie.view.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.data.PermissionSharedPreferences

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (!checkPermissionState(context)) return
        val movieTitle =
            intent.getStringExtra(ALARM_MOVIE_TITLE_KEY)
                ?: throw IllegalStateException(INVALID_NOTIFICATION_MOVIE_TITLE)

        val ticketId = intent.getLongExtra(ALARM_TICKET_ID_KEY, DEFAULT_TICKET_ID)

        if (ticketId == DEFAULT_TICKET_ID) {
            throw IllegalStateException(
                INVALID_NOTIFICATION_TICKET_ID,
            )
        }
        val helper = NotificationHelper(context)
        val notification = helper.notification(ticketId, movieTitle)
        helper.notify(ticketId.toInt(), notification)
    }

    private fun checkPermissionState(context: Context): Boolean {
        val prefs = PermissionSharedPreferences(context)
        return prefs.notificationPermission()
    }

    companion object {
        fun newIntent(
            context: Context,
            movieTitle: String,
            ticketId: Long,
        ): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra(ALARM_MOVIE_TITLE_KEY, movieTitle)
                putExtra(ALARM_TICKET_ID_KEY, ticketId)
            }
        }

        private const val ALARM_MOVIE_TITLE_KEY = "ALARM_MOVIE_TITLE_KEY"
        private const val ALARM_TICKET_ID_KEY = "ALARM_TICKET_ID_KEY"

        private const val DEFAULT_TICKET_ID = 0L
        private const val INVALID_NOTIFICATION_MOVIE_TITLE =
            "$ALARM_MOVIE_TITLE_KEY is missing in the Intent."
        private const val INVALID_NOTIFICATION_TICKET_ID =
            "$ALARM_TICKET_ID_KEY is missing in the Intent."
    }
}
