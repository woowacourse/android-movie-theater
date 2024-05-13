package woowacourse.movie.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.home.HomeActivity

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservation =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                intent.getParcelableExtra("reservation", Reservation::class.java)
            } else {
                intent.getParcelableExtra("reservation")
            }

        if (reservation != null) {
            val notification = NotificationManager(context).buildReservationNotification(context, reservation)

            with(NotificationManagerCompat.from(context)) {
                if (ActivityCompat.checkSelfPermission(
                        context,
                        Manifest.permission.POST_NOTIFICATIONS,
                    ) == PackageManager.PERMISSION_GRANTED &&
                    HomeActivity.sharedPreference.isPushNotificationActivated()
                ) {
                    notify(1, notification)
                }
            }
        }
    }
}
