package woowacourse.movie.ui.confirm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import java.time.ZoneId
import woowacourse.movie.R
import woowacourse.movie.model.TicketsState
import woowacourse.movie.ui.BackKeyActionBarActivity
import woowacourse.movie.util.getParcelableExtraCompat
import woowacourse.movie.util.keyError

class ReservationConfirmActivity : BackKeyActionBarActivity() {
    private lateinit var view: ReservationConfirmView

    override fun onCreateView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_reservation_confirm)
        val tickets = intent.getParcelableExtraCompat<TicketsState>(KEY_TICKETS)
            ?: return keyError(KEY_TICKETS)
        view = ReservationConfirmView(window.decorView.rootView, tickets)
        setNotification(tickets)
    }

    private fun setNotification(tickets: TicketsState) {
        val alarmManager: AlarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val alarmIntent = AlarmReceiver.getIntent(this, tickets).let { intent ->
            PendingIntent.getBroadcast(
                this,
                tickets.hashCode(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT + PendingIntent.FLAG_IMMUTABLE
            )
        }

        val advanceNoticeDateTime = tickets.dateTime
            .minusMinutes(ADVANCE_NOTICE_MINUTES)
            .atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            advanceNoticeDateTime,
            alarmIntent
        )
    }

    companion object {
        private const val KEY_TICKETS = "key_tickets"
        private const val ADVANCE_NOTICE_MINUTES = 30L

        fun startActivity(context: Context, tickets: TicketsState) {
            val intent = Intent(context, ReservationConfirmActivity::class.java).apply {
                putExtra(KEY_TICKETS, tickets)
            }
            context.startActivity(intent)
        }
    }
}
