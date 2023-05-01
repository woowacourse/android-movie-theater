package woowacourse.movie.broadcastreceiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.data.storage.SettingsStorage
import woowacourse.movie.notification.NotificationManager
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.utils.getParcelable

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (checkPushNotificationAllowed()) return

        val ticketModel =
            intent.getParcelable<MovieTicketModel>(TICKET_EXTRA_KEY) ?: return

        createNotificationChannel(context)

        notifyReservation(context, ticketModel)
    }

    private fun checkPushNotificationAllowed() = !SettingsStorage.enablePushNotification

    private fun createNotificationChannel(context: Context) {
        val name = context.getString(R.string.reservation_notification_title)
        val description = context.getString(R.string.reservation_notification_channel_description)
        NotificationManager.createChannel(context, CHANNEL_ID, name, description)
    }

    private fun notifyReservation(
        context: Context,
        ticketModel: MovieTicketModel
    ) {
        val icon = R.drawable.ic_reservation_notification
        val title = context.getString(R.string.reservation_notification_title)
        val content = context.getString(
            R.string.reservation_notification_content,
            ticketModel.title
        )
        val ticketIntent = MovieTicketActivity.createIntent(context, ticketModel)
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                ticketModel.hashCode(),
                ticketIntent,
                PendingIntent.FLAG_IMMUTABLE
            )

        NotificationManager.notify(
            context,
            CHANNEL_ID,
            NOTIFICATION_ID,
            icon,
            title,
            content,
            pendingIntent
        )
    }

    companion object {
        private const val CHANNEL_ID = "reservation"
        private const val NOTIFICATION_ID = 1
        private const val TICKET_EXTRA_KEY = "ticket"

        fun createIntent(context: Context, ticket: MovieTicketModel): Intent {
            val intent = Intent(context, NotificationReceiver::class.java)
            intent.putExtra(TICKET_EXTRA_KEY, ticket)
            return intent
        }
    }
}
