package woowacourse.movie.purchaseConfirmation

import android.content.Context
import woowacourse.movie.database.AppDatabase
import woowacourse.movie.database.Ticket
import kotlin.concurrent.thread

class TicketModel(context: Context) : PurchaseConfirmationContract.Model {
    private val appDatabase = AppDatabase.getInstance(context)

    override fun getTicket(ticketId: Int, callback: (Ticket?) -> Unit, errorCallback: () -> Unit) {
        thread {
            try {
                val ticket = appDatabase.ticketDao().getTicketById(ticketId)
                callback(ticket)
            } catch (e: Exception) {
                errorCallback()
            }
        }
    }
}
