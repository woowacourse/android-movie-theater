package woowacourse.movie.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import androidx.core.app.NotificationCompat
import woowacourse.MovieApplication.Companion.database
import woowacourse.MovieApplication.Companion.sharedPrefs
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.result.view.MovieResultActivity
import woowacourse.movie.util.MovieIntent.MOVIE_TICKET_ID

class MovieNotificationReceiver : BroadcastReceiver() {
    private lateinit var reservationHistoryEntity: ReservationHistoryEntity

    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        registrationChannel(notificationManager)

        val ticketId =
            intent.getLongExtra(
                MOVIE_TICKET_ID.key,
                MOVIE_TICKET_ID.invalidValue as Long,
            )

        val thread =
            Thread {
                reservationHistoryEntity =
                    database.reservationHistoryDao()
                        .findReservationHistoryById(
                            ticketId,
                        )
            }
        thread.start()
        thread.join()

        val pendingIntent = createResultActivityPendingIntent(context, ticketId)
        val notification =
            buildNotification(context, reservationHistoryEntity.movieId, pendingIntent)
        if (sharedPrefs.getSavedAlarmSetting()) {
            notificationManager.notify(
                NOTIFICATION_ID,
                notification,
            )
        }
    }

    private fun registrationChannel(notificationManager: NotificationManager) {
        val channel =
            NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                IMPORTANCE_DEFAULT,
            )

        notificationManager.createNotificationChannel(channel)
    }

    private fun buildNotification(
        context: Context,
        movieId: Long,
        pendingIntent: PendingIntent,
    ): Notification? {
        val movieData = getMovieById(movieId)
        return movieData?.let { movie ->
            NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_movie)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, movie.image))
                .setContentTitle(movie.title)
                .setContentText(context.getString(R.string.notification_content, movie.title))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .build()
        }
    }

    private fun createResultActivityPendingIntent(
        context: Context,
        ticketId: Long,
    ): PendingIntent {
        val resultActivityIntent =
            MovieResultActivity.createIntent(
                context,
                ticketId,
            )
        return PendingIntent.getActivity(
            context,
            PENDING_REQUEST_CODE,
            resultActivityIntent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "notification_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "movieNotification"
        const val NOTIFICATION_ID = 1
        const val PENDING_REQUEST_CODE = 0

        fun createIntent(
            context: Context,
            ticketId: Long,
        ): Intent {
            return Intent(context, MovieNotificationReceiver::class.java).apply {
                putExtra(MOVIE_TICKET_ID.key, ticketId)
            }
        }
    }
}
