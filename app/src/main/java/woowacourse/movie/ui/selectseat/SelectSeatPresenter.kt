package woowacourse.movie.ui.selectseat

import android.widget.Button
import domain.Ticket
import domain.TicketOffice
import woowacourse.movie.data.model.SeatTable
import woowacourse.movie.data.model.SeatView
import woowacourse.movie.data.model.uimodel.SeatUiModel
import java.time.LocalDateTime

class SelectSeatPresenter(
    private val view: SelectSeatContract.View,
    private val seatTable: SeatTable,
    private val ticketOffice: TicketOffice
) : SelectSeatContract.Presenter {

    override val tickets
        get() = ticketOffice.tickets

    override fun setSeatTable() {
        seatTable.makeSeatTable()
    }

    override fun updateSeatState(seatView: SeatView, dateTime: LocalDateTime) {
        val ticket =
            ticketOffice.generateTicket(
                dateTime,
                SeatUiModel.toNumber(seatView.row),
                seatView.col
            )
        if (ticketOffice.tickets.isContainSameTicket(ticket)) {
            setViewNotSelected(seatView, ticket)
        } else {
            setViewSelected(seatView, ticket)
        }
    }

    private fun setViewNotSelected(seatView: SeatView, ticket: Ticket) {
        ticketOffice.deleteTicket(ticket)
        view.updateSeatView(seatView, false)
    }

    private fun setViewSelected(seatView: SeatView, ticket: Ticket) {
        if (!ticketOffice.isAvailableAddTicket()) {
            return
        }
        ticketOffice.addTicket(ticket)
        view.updateSeatView(seatView, true)
    }

    override fun updateButtonState(button: Button) {
        val isEnabled = ticketOffice.isAvailableAddTicket()
        button.isEnabled = !isEnabled
    }
}
