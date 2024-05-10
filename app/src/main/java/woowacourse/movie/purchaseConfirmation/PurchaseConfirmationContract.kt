package woowacourse.movie.purchaseConfirmation

import woowacourse.movie.database.Ticket

interface PurchaseConfirmationContract {
    interface Model {
        fun getTicket(
            ticketId: Int,
            callback: (Ticket?) -> Unit,
            errorCallback: () -> Unit,
        )
    }

    interface View {
        fun showTicketInfo(ticket: Ticket)

        fun showError()

        fun navigateBack()
    }
}
