package woowacourse.movie.purchaseConfirmation

class PurchaseConfirmationPresenter(
    private val view: PurchaseConfirmationContract.View,
    private val model: PurchaseConfirmationContract.Model,
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
