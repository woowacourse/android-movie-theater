package woowacourse.movie.ui.selectseat

import android.widget.Button
import domain.Reservation
import domain.Ticket
import domain.TicketOffice
import woowacourse.movie.data.model.SeatTable
import woowacourse.movie.data.model.SeatView
import woowacourse.movie.data.model.mapper.TheaterMapper
import woowacourse.movie.data.model.uimodel.SeatUIModel
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import woowacourse.movie.db.DBHelper
import java.time.LocalDateTime

class SelectSeatPresenter(
    private val view: SelectSeatContract.View,
    private val seatTable: SeatTable,
    private val ticketOffice: TicketOffice,
    private val dbHelper: DBHelper
) : SelectSeatContract.Presenter {

    override val tickets
        get() = ticketOffice.tickets

    override fun setSeatTable() {
        seatTable.makeSeatTable()
    }

    override fun updateSeatState(seatView: SeatView, dateTime: LocalDateTime, theater: TheaterUIModel) {
        val ticket =
            ticketOffice.generateTicket(
                dateTime,
                SeatUIModel.toNumber(seatView.row),
                seatView.col,
                TheaterMapper.toDomain(theater)
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

    override fun updateReservation(reservation: Reservation) {
        dbHelper.saveReservation(reservation)
    }
}
