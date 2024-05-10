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
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.result.view.MovieResultActivity
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_RESERVATION_COUNT
import woowacourse.movie.util.MovieIntentConstant.INVALID_VALUE_THEATER_POSITION
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_DATE
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_ID
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_SEATS
import woowacourse.movie.util.MovieIntentConstant.KEY_MOVIE_TIME
import woowacourse.movie.util.MovieIntentConstant.KEY_RESERVATION_COUNT
import woowacourse.movie.util.MovieIntentConstant.KEY_SELECTED_THEATER_POSITION

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
                intent.getStringExtra(KEY_MOVIE_DATE) ?: INVALID_VALUE_MOVIE_DATE,
                intent.getStringExtra(KEY_MOVIE_TIME) ?: INVALID_VALUE_MOVIE_TIME,
                intent.getIntExtra(KEY_RESERVATION_COUNT, INVALID_VALUE_RESERVATION_COUNT),
                intent.getStringExtra(KEY_MOVIE_SEATS) ?: INVALID_VALUE_MOVIE_SEATS,
                intent.getLongExtra(KEY_MOVIE_ID, INVALID_VALUE_MOVIE_ID),
                intent.getIntExtra(KEY_SELECTED_THEATER_POSITION, INVALID_VALUE_THEATER_POSITION),
            )

        val pendingIntent = createPendingIntent(context, reservationHistoryEntity)
        val notification =
            buildNotification(context, reservationHistoryEntity.movieId, pendingIntent)
        notificationManager.notify(NOTIFICATION_ID, notification)
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
        private const val NOTIFICATION_ID = 1
        private const val PENDING_REQUEST_CODE = 0
    }
}
