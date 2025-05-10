package woowacourse.movie

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import woowacourse.movie.mapper.IntentCompat
import woowacourse.movie.ui.model.TicketUiModel

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(
        context: Context,
        intent: Intent,
    ) {
        val ticket = IntentCompat.getParcelableExtra(intent, KEY_TICKET_ALARM, TicketUiModel::class.java)

        Toast.makeText(context, ticket.toString(), Toast.LENGTH_SHORT).show()
    }

    companion object {
        private const val KEY_TICKET_ALARM = "TICKET_ALARM_DATA"

        fun newIntent(
            context: Context,
            ticket: TicketUiModel,
        ): Intent =
            Intent(context, AlarmReceiver::class.java).apply {
                putExtra(KEY_TICKET_ALARM, ticket)
            }
    }
}
