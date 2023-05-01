package woowacourse.movie.broadcastreceiver

import android.Manifest
import android.annotation.SuppressLint
import android.app.PendingIntent
import android.content.Context
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.R
import woowacourse.movie.data.MovieData
import woowacourse.movie.presentation.complete.CompleteActivity
import woowacourse.movie.presentation.model.TicketModel
import woowacourse.movie.presentation.util.checkPermissionTiramisu

object BookedTicketNotification {

    @SuppressLint("MissingPermission")
    fun sendNotification(
        context: Context,
        ticket: TicketModel,
    ) {
        val builder =
            NotificationCompat.Builder(context, context.getString(R.string.alarm_channel_id))
                .setSmallIcon(R.drawable.ic_home)
                .setContentTitle(context.getString(R.string.booked_ticket_notification_title))
                .setContentText(
                    context.getString(
                        R.string.booked_ticket_notification_content,
                        MovieData.findMovieById(ticket.movieId).title,
                    ),
                )
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(getPendingIntent(context, ticket))
                .setAutoCancel(true)

        if (hasNotificationPermission(context)) {
            NotificationManagerCompat.from(context).notify(1, builder.build())
        }
    }

    private fun getPendingIntent(
        context: Context,
        ticket: TicketModel,
    ): PendingIntent? {
        val intent = CompleteActivity.getIntent(context, ticket)

        return PendingIntent.getActivity(
            context,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE,
        )
    }

    @SuppressLint("InlinedApi")
    private fun hasNotificationPermission(context: Context) =
        context.checkPermissionTiramisu(Manifest.permission.POST_NOTIFICATIONS)
}
