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
import woowacourse.movie.MovieMainActivity.Companion.sharedPrefs
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.result.view.MovieResultActivity
import woowacourse.movie.util.MovieIntent.MOVIE_DATE
import woowacourse.movie.util.MovieIntent.MOVIE_ID
import woowacourse.movie.util.MovieIntent.MOVIE_SEATS
import woowacourse.movie.util.MovieIntent.MOVIE_TIME
import woowacourse.movie.util.MovieIntent.RESERVATION_COUNT
import woowacourse.movie.util.MovieIntent.SELECTED_THEATER_POSITION

class MovieNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        registrationChannel(notificationManager)
        val reservationHistoryEntity =
            ReservationHistoryEntity(
                intent.getStringExtra(MOVIE_DATE.key) ?: MOVIE_DATE.invalidValue as String,
                intent.getStringExtra(MOVIE_TIME.key) ?: MOVIE_TIME.invalidValue as String,
                intent.getIntExtra(RESERVATION_COUNT.key, RESERVATION_COUNT.invalidValue as Int),
                intent.getStringExtra(MOVIE_SEATS.key) ?: MOVIE_SEATS.invalidValue as String,
                intent.getLongExtra(MOVIE_ID.key, MOVIE_ID.invalidValue as Long),
                intent.getIntExtra(
                    SELECTED_THEATER_POSITION.key,
                    SELECTED_THEATER_POSITION.invalidValue as Int,
                ),
            )

        val pendingIntent = createPendingIntent(context, reservationHistoryEntity)
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

    private fun createPendingIntent(
        context: Context,
        reservationHistoryEntity: ReservationHistoryEntity,
    ): PendingIntent {
        val resultActivityIntent =
            MovieResultActivity.createIntent(
                context,
                reservationHistoryEntity.movieId,
                reservationHistoryEntity.date,
                reservationHistoryEntity.time,
                reservationHistoryEntity.count,
                reservationHistoryEntity.seats,
                reservationHistoryEntity.theaterPosition,
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
            movieId: Long,
            date: String,
            time: String,
            count: Int,
            seats: String,
            theaterPosition: Int,
        ): Intent {
            return Intent(context, MovieNotificationReceiver::class.java).apply {
                putExtra(MOVIE_ID.key, movieId)
                putExtra(MOVIE_DATE.key, date)
                putExtra(MOVIE_TIME.key, time)
                putExtra(RESERVATION_COUNT.key, count)
                putExtra(MOVIE_SEATS.key, seats)
                putExtra(SELECTED_THEATER_POSITION.key, theaterPosition)
            }
        }
    }
}
