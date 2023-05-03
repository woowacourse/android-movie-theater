package woowacourse.movie.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
import woowacourse.movie.uimodel.MovieTicketModel
import woowacourse.movie.utils.getSerializableExtraCompat

class ReservationAlarmReceiver : BroadcastReceiver() {

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationManager = NotificationCreator(context)
        val movie: MovieTicketModel =
            intent.getSerializableExtraCompat(MovieDetailActivity.KEY_MOVIE)
                ?: return

        notificationManager.createNotificationChannel()
        notificationManager.makeNotification(movie)
    }

    companion object {
        fun getIntent(ticketModel: MovieTicketModel, context: Context): Intent {
            val intent = Intent(context, ReservationAlarmReceiver::class.java).apply {
                putExtra(MovieDetailActivity.KEY_MOVIE, ticketModel)
            }

            return intent
        }
    }
}
