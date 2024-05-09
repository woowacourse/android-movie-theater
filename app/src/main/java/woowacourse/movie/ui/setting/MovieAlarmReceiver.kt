package woowacourse.movie.ui.setting

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.ui.complete.MovieReservationCompleteActivity

class MovieAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        if (getAlarmChecked(context)) {
            val userTicketId = userTicketId(intent)
            val movieTitle = movieTitle(intent)
            Log.e("seogi", "title: $movieTitle   id: $userTicketId")
            val movieIntent =
                Intent(context, MovieReservationCompleteActivity::class.java).apply {
                    putExtra("ticket_id", userTicketId)
                }

            MovieAlarmNotificationBuilder(context, movieTitle, movieIntent).notifyAlarm()
        }
    }

    private fun userTicketId(intent: Intent) = intent.getLongExtra("ticket_id", -1L)

    private fun movieTitle(intent: Intent) = intent.getStringExtra("movie_title_id") ?: throw IllegalStateException()

    private fun getAlarmChecked(context: Context?): Boolean {
        val sharedPreference =
            context?.getSharedPreferences("settings", Context.MODE_PRIVATE)
        return sharedPreference?.getBoolean("settings", false) ?: false
    }
}
