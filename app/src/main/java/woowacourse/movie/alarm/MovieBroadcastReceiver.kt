package woowacourse.movie.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import woowacourse.movie.database.TicketDatabase
import woowacourse.movie.ticket.model.DbTicket
import woowacourse.movie.ticket.view.MovieTicketActivity
import java.util.concurrent.CountDownLatch

class MovieBroadcastReceiver : BroadcastReceiver() {
    private lateinit var tickets: List<DbTicket>
    override fun onReceive(context: Context, intent: Intent?) {
        val ticketDb = TicketDatabase.getDatabase(context)
        val latch = CountDownLatch(1)
        Thread {
            tickets = ticketDb.ticketDao().getAll()
            latch.countDown()
        }.start()
        latch.await()
        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder: NotificationCompat.Builder
        val channelId = "one-channel"
        val channelName = "My Channel One"
        val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        manager.createNotificationChannel(channel)
        builder = NotificationCompat.Builder(context, channelId)

        val ticketIntent = MovieTicketActivity.newTicketActivityInstance(context, tickets, 1)
        val pendingIntent = PendingIntent.getActivity(context, 10, ticketIntent, PendingIntent.FLAG_MUTABLE)

        setBuilderInfo(builder, pendingIntent)
        manager.notify(1, builder.build())
    }

    private fun setBuilderInfo(builder: NotificationCompat.Builder, pendingIntent: PendingIntent) {
        builder.setSmallIcon(android.R.drawable.ic_popup_reminder)
        builder.setContentTitle("예매 알림")
        builder.setContentText("30분 후에 상영")
        builder.setContentIntent(pendingIntent)
    }
}
