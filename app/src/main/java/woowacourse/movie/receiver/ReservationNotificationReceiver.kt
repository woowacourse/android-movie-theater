package woowacourse.movie.receiver

import android.R
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.mock.MockMoviesFactory
import woowacourse.movie.view.activity.ReservationResultActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.model.mapper.MovieMapper.toUi
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TicketsUiModel
import java.time.LocalDateTime
import java.time.LocalTime

class ReservationNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, receivedIntent: Intent) {
        val movieUiModel = receiveMovieViewModel(receivedIntent)
        val ticketsUiModel = receiveTicketsUiModel(receivedIntent)
        val sendingIntent =
            ReservationResultActivity.generateIntent(context, movieUiModel, ticketsUiModel)
        val pendingIntent = PendingIntent.getActivity(
            context,
            LocalDateTime.now().second,
            sendingIntent,
            PendingIntent.FLAG_MUTABLE
        )

        val notificationData = NotificationData(
            title = context.getString(woowacourse.movie.R.string.reservation_notification_title),
            contextText = "${movieUiModel.title} ${context.getString(woowacourse.movie.R.string.reservation_notification_description)}",
            smallIcon = R.drawable.ic_lock_idle_alarm,
            isAutoCancel = true,
            pendingIntent = pendingIntent
        )
        val notificationManager = NotificationHelper.generateNotificationManger(
            context,
            CHANNEL_ID,
            CHANNEL_NAME
        )
        val notification =
            NotificationHelper.generateNotification(context, CHANNEL_ID, notificationData)
        NotificationHelper.notifyNotification(notificationManager, notification, NOTIFICATION_ID)
    }

    private fun receiveTicketsUiModel(intent: Intent): TicketsUiModel {
        return intent.extras?.getSerializableCompat(KEY_TICKETS_VALUE) ?: TicketsUiModel(listOf())

    }

    private fun receiveMovieViewModel(intent: Intent): MovieUiModel {
        return intent.extras?.getSerializableCompat(KEY_MOVIE_VALUE)
            ?: MockMoviesFactory.generateMovie(0).toUi()
    }

    companion object {
        fun generateReceiverIntent(
            context: Context,
            movieUiModel: MovieUiModel,
            ticketsUiModel: TicketsUiModel
        ): Intent {
            val receiverIntent = Intent(context, ReservationNotificationReceiver::class.java)
            receiverIntent.putExtra(KEY_MOVIE_VALUE, movieUiModel)
            return receiverIntent.putExtra(KEY_TICKETS_VALUE, ticketsUiModel)
        }

        private const val CHANNEL_ID = "channel1"
        private const val CHANNEL_NAME = "Channel1"
        private const val KEY_MOVIE_VALUE = "KEY_MOVIE_VALUE"
        private const val KEY_TICKETS_VALUE = "KEY_TICKETS_VALUE"
        private const val NOTIFICATION_ID = 1
    }
}
