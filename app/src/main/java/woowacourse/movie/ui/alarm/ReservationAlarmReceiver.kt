package woowacourse.movie.ui.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.ui.moviedetail.MovieDetailActivity
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
            val intent = Intent(context, this::class.java).apply {
                putExtra(MovieDetailActivity.KEY_MOVIE, ticketModel)
            }

            return intent
        }
    }
}
