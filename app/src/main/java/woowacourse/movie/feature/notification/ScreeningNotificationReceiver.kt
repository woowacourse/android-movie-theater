package woowacourse.movie.feature.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.feature.finished.ReservationFinishedActivity
import woowacourse.movie.feature.notification.ScreeningAlarm.Companion.SCREENING_NOTIFICATION
import woowacourse.movie.model.movie.ScreeningNotification
import woowacourse.movie.utils.MovieUtils.intentSerializable

class ScreeningNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val notification = ScreeningNotificationManager(context)

        val screeningInformation: ScreeningNotification? =
            intent.intentSerializable(
                SCREENING_NOTIFICATION,
                ScreeningNotification::class.java,
            )

        if (screeningInformation != null) {
            val newIntent = Intent(context, ReservationFinishedActivity::class.java).putExtra(SCREENING_NOTIFICATION, screeningInformation)
            notification.deliver(newIntent, screeningInformation)
        }
    }
}
