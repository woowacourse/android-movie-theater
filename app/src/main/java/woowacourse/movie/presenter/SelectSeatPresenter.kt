package woowacourse.movie.presenter

import domain.Reservation
import domain.Seat
import woowacourse.movie.contract.SelectSeatContract
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.model.TicketOfficeUiModel
import woowacourse.movie.model.mapper.MovieMapper.toDomain
import woowacourse.movie.model.mapper.TicketOfficeMapper.toDomain
import woowacourse.movie.model.mapper.TicketsMapper.toUi
import woowacourse.movie.database.ReservationDbHelperInterface
import woowacourse.movie.view.widget.SeatView

class SelectSeatPresenter(
    val view: SelectSeatContract.View,
    val ticketOfficeUiModel: TicketOfficeUiModel,
    val movieUiModel: MovieUiModel,
    val reservationDbHelper: ReservationDbHelperInterface
) : SelectSeatContract.Presenter {
    private val ticketOffice = ticketOfficeUiModel.toDomain()

    override fun onClickSeat(seatView: SeatView) {
        val seat = Seat(SeatUiModel.toNumber(seatView.row), seatView.col)
        val ticket = ticketOffice.generateTicket(
            seat.row, seat.col
        )
        ticketOffice.updateTickets(ticket)
        onSeatStateChange()
        onPriceTextChange()
        onCheckButtonStateChange()
    }

    override fun onSeatStateChange() {
        val ticketsUiModel = ticketOffice.tickets.toUi()
        view.updateSeats(ticketsUiModel)
    }

    override fun onPriceTextChange() {
        view.setPriceText(ticketOffice.tickets.price.value)
    }

    override fun onCheckButtonStateChange() {
        val isClickAble = !ticketOffice.isAvailableAddTicket()
        view.setCheckButtonClickable(isClickAble)
        view.setCheckButtonColor(isClickAble)
    }

    override fun onClickCheckButton() {
        view.showDialog()
    }

    override fun onClickDialogPositiveButton() {
        view.startReservationResultActivity(movieUiModel, ticketOffice.tickets.toUi())
        view.registerAlarm(movieUiModel, ticketOffice.tickets.toUi())
        val reservation = Reservation(movieUiModel.toDomain(), ticketOffice.tickets)
        reservationDbHelper.saveReservation(reservation)
    }

    override fun onClickDialogCancelButton() {
        view.cancelDialog()
    }
}
