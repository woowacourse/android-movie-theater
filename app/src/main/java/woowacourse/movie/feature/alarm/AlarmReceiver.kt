package woowacourse.movie.feature.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import woowacourse.movie.R
import woowacourse.movie.data.AlarmSettingRepositoryImpl
import woowacourse.movie.model.TicketsState
import woowacourse.movie.util.getParcelableCompat
import woowacourse.movie.util.sendNotification

class AlarmReceiver : BroadcastReceiver(), AlarmReceiverContract.View {

    private lateinit var presenter: AlarmReceiverContract.Presenter
    private lateinit var context: Context
    override fun onReceive(context: Context, intent: Intent?) {
        val tickets: TicketsState = intent?.extras?.getParcelableCompat(TICKETS) ?: return
        this.context = context
        presenter = AlarmReceiverPresenter(this, AlarmSettingRepositoryImpl)
        presenter.receiveAlarmSignal(tickets)
    }

    override fun generateMovieAlarmNotification(tickets: TicketsState) {
        context.sendNotification(
            R.drawable.ic_launcher_foreground,
            context.getString(R.string.alarm_receiver_notification_title),
            context.getString(R.string.alarm_receiver_notification_text)
                .format(tickets.movieState.title),
            true
        )
    }

    companion object {
        fun getIntent(context: Context, tickets: TicketsState): Intent {
            return Intent(context, AlarmReceiver::class.java).apply { putExtra(TICKETS, tickets) }
        }

        private const val TICKETS = "tickets"
    }
}
