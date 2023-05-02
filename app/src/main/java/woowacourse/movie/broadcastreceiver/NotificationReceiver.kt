package woowacourse.movie.broadcastreceiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.R
import woowacourse.movie.notification.NotificationCreator
import woowacourse.movie.ui.activity.MovieTicketActivity
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.storage.SettingsStorage
import woowacourse.movie.ui.utils.getParcelable

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d("sunny", "onReceive")

        if (checkPushNotificationAllowed()) return

        Log.d("sunny", "allow permission")

        val ticketModel =
            intent.getParcelable<MovieTicketModel>(TICKET_EXTRA_KEY) ?: return

        Log.d("sunny", "got intent Data")

        notifyReservation(context, ticketModel)
    }

    private fun checkPushNotificationAllowed() = !SettingsStorage.getPushNotification()

    private fun notifyReservation(context: Context, ticketModel: MovieTicketModel) {
        val notificationCreator = NotificationCreator(context, CHANNEL_ID)
        registerNotificationChannel(notificationCreator, context)
        setNotificationView(notificationCreator, context, ticketModel)

        notificationCreator.notify(NOTIFICATION_ID)
    }

    private fun registerNotificationChannel(
        notificationCreator: NotificationCreator,
        context: Context
    ) {
        notificationCreator.registerNotificationChannel(
            context.getString(R.string.reservation_notification_title),
            context.getString(R.string.reservation_notification_channel_description)
        )
    }

    private fun setNotificationView(
        notificationCreator: NotificationCreator,
        context: Context,
        ticketModel: MovieTicketModel
    ) {
        notificationCreator.setNotificationView(
            R.drawable.ic_reservation_notification,
            context.getString(R.string.reservation_notification_title),
            context.getString(R.string.reservation_notification_content, ticketModel.title),
            getPendingIntent(context, ticketModel)
        )
    }

    private fun getPendingIntent(
        context: Context,
        ticketModel: MovieTicketModel
    ): PendingIntent {
        return PendingIntent.getActivity(
            context,
            ticketModel.hashCode(),
            MovieTicketActivity.createIntent(context, ticketModel),
            PendingIntent.FLAG_IMMUTABLE
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
