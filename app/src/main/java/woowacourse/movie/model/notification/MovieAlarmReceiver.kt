package woowacourse.movie.model.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.model.data.MovieSharedPreferenceImpl
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity
import woowacourse.movie.ui.setting.MovieSettingKey

class MovieAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (MovieSharedPreferenceImpl.getAlarmChecked()) {
            val userTicketId = userTicketId(intent)
            val movieTitle = movieTitle(intent)
            val movieIntent =
                Intent(context, MovieReservationCompleteActivity::class.java).apply {
                    putExtra(MovieSettingKey.TICKET_ID, userTicketId)
                }

            MovieAlarmNotificationBuilder(
                context,
                movieTitle,
                userTicketId.toInt(),
                movieIntent,
            ).notifyAlarm()
        }
    }

    private fun userTicketId(intent: Intent) = intent.getLongExtra(MovieSettingKey.TICKET_ID, -1L)

    private fun movieTitle(intent: Intent) = intent.getStringExtra(MovieSettingKey.MOVIE_TITLE_ID) ?: throw IllegalStateException()
}
