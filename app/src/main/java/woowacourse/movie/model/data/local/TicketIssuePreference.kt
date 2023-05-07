package woowacourse.movie.model.data.local

import android.content.Context
import android.content.Context.MODE_PRIVATE
import woowacourse.movie.model.data.storage.TicketIssueStorage

class TicketIssuePreference(context: Context) : TicketIssueStorage {
    private val value by lazy {
        context.getSharedPreferences(TICKET_ISSUE, MODE_PRIVATE)
    }

    override fun issueTicketId(): Int {
        val ticketId = value.getInt(TICKET_ID, 1)
        value.edit().putInt(TICKET_ID, ticketId + 1).apply()
        return ticketId
    }

    companion object {
        private const val TICKET_ISSUE = "TicketIssue"

        private val TICKET_ID = "TicketId"
    }
}
