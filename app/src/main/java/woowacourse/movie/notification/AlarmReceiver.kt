package woowacourse.movie.notification

import android.Manifest
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationManagerCompat
import woowacourse.movie.model.Reservation
import woowacourse.movie.presentation.home.HomeActivity
import woowacourse.movie.utils.getParcelableReservationExtra

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val reservation = intent.getParcelableReservationExtra(KEY_RESERVATION)

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

    companion object {
        private const val KEY_RESERVATION = "reservation"

        fun createIntent(
            context: Context,
            reservation: Reservation,
        ): Intent {
            return Intent(context, AlarmReceiver::class.java).apply {
                putExtra(KEY_RESERVATION, reservation)
            }
        }
    }
}
