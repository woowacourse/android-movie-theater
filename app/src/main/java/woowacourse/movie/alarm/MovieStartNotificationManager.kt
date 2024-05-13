package woowacourse.movie.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import woowacourse.movie.R
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.result.ReservationResultActivity

class MovieStartNotificationManager(
    val context: Context,
    val intent: Intent,
) {
    private val notificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    private lateinit var notificationBuilder: NotificationCompat.Builder

    fun startNotification() {
        val ticket = intent.getParcelableExtra<MovieTicketUiModel>("ticket")!!

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "notification_before_screening"
            val name = "영화 상영 시작 전 알림"
            val descriptionText = "영화 상영 30분 전 사용자에게 알림을 발송합니다."
            val importance = NotificationManager.IMPORTANCE_HIGH

            val notificationChannel = NotificationChannel(channelId, name, importance).apply {
                description = descriptionText
            }

            notificationManager.createNotificationChannel(notificationChannel)

            notificationBuilder = NotificationCompat.Builder(context, channelId)
        } else {
            notificationBuilder = NotificationCompat.Builder(context)
        }

        notificationBuilder.setSmallIcon(R.drawable.ic_home)
            .setContentTitle("영화")
            .setContentText("영화 상영 시작 30분 전입니다.")
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)

        pendingIntent(notificationBuilder, ticket)
        notificationManager.notify(ticket.ticketId.toInt(), notificationBuilder.build())
    }

    private fun pendingIntent(
        builder: NotificationCompat.Builder,
        movieTicketUiModel: MovieTicketUiModel,
    ) {
        val actionIntent = Intent(context, ReservationResultActivity::class.java)
        actionIntent.putExtra(ReservationResultActivity.INTENT_TICKET, movieTicketUiModel)
        val actionPending =
            PendingIntent.getActivity(
                context,
                20,
                actionIntent,
                PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE,
            )
        builder.setContentIntent(actionPending)
    }
}
