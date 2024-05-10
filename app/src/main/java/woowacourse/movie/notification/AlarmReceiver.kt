package woowacourse.movie.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import woowacourse.movie.model.Reservation

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notification = Notification(context)
        val reservation =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("reservation", Reservation::class.java)
            } else {
                intent.getParcelableExtra("reservation")
            }
        if (reservation != null) {
            notification.buildNotification(context, reservation)
        }
    }
}
