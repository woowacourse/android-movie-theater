package woowacourse.movie.receiver

import android.R
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.MockMoviesFactory
import woowacourse.movie.SettingPreferencesManager
import woowacourse.movie.activity.ReservationResultActivity
import woowacourse.movie.getSerializableCompat
import woowacourse.movie.view.mapper.MovieMapper
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.model.TicketsUiModel

class ReservationNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, receivedIntent: Intent) {
        val reservationNotificationHelper = NotificationHelper(context, CHANNEL_ID, CHANNEL_NAME)
        val movieUiModel = receiveMovieViewModel(receivedIntent)
        val ticketsUiModel = receiveTicketsUiModel(receivedIntent)
        val sendingIntent =
            ReservationResultActivity.generateIntent(context, movieUiModel, ticketsUiModel)
        val pendingIntent = PendingIntent.getActivity(
            context, REQUEST_CODE, sendingIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val notificationData = NotificationData(
            title = context.getString(woowacourse.movie.R.string.reservation_notification_title),
            contextText = "${movieUiModel.title} context.getString(woowacourse.movie.R.string.reservation_notification_description)",
            smallIcon = R.drawable.ic_lock_idle_alarm,
            isAutoCancel = true,
            pendingIntent = pendingIntent
        )

        val notificationManager = reservationNotificationHelper.generateNotificationManger()
        val notification = reservationNotificationHelper.generateNotification(
            notificationData,
            notificationManager
        )
        if (SettingPreferencesManager.getAlarmReceptionStatus()) notificationManager.notify(
            NOTIFICATION_ID,
            notification
        )
    }

    private fun receiveTicketsUiModel(intent: Intent): TicketsUiModel {
        return intent.extras?.getSerializableCompat(KEY_TICKETS_VALUE) ?: TicketsUiModel(listOf())

    }

    private fun receiveMovieViewModel(intent: Intent): MovieUiModel {
        return intent.extras?.getSerializableCompat(KEY_MOVIE_VALUE) ?: MovieMapper.toUi(
            MockMoviesFactory.generateMovie(0)
        )
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
        private const val REQUEST_CODE = 123
        private const val NOTIFICATION_ID = 1
    }
}
