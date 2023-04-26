package woowacourse.movie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import woowacourse.movie.activity.SeatSelectionActivity

class ReservationAlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == SeatSelectionActivity.ACTION_ALARM) {
            Log.d("DYDY", "ALARM")
        }
    }
}
