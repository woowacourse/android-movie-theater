package woowacourse.movie.purchaseConfirmation

import android.content.Context

class PurchaseConfirmationPresenter(
    private val view: PurchaseConfirmationContract.View,
    private val model: PurchaseConfirmationContract.Model,
    private val context: Context
) {
    fun loadTicket(ticketId: Int) {
        if (ticketId == 0) {
            view.showError()
            return
        }

        model.getTicket(ticketId, { ticket ->
            ticket?.let {
                view.showTicketInfo(it)
            } ?: run {
                view.showError()
            }
        }, {
            view.showError()
        })
    }
}
