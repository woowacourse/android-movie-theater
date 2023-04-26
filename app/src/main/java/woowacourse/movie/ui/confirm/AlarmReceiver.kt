package woowacourse.movie.ui.confirm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableCompat

class AlarmReceiver() : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        val tickets = intent?.extras?.getParcelableCompat<TicketsState>("a")
    }
}
