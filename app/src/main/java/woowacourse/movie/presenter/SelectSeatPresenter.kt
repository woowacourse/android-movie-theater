package woowacourse.movie.presenter

import domain.Reservation
import domain.Seat
import domain.TicketOffice
import domain.Tickets
import domain.discountPolicy.DisCountPolicies
import woowacourse.movie.contract.SelectSeatContract
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.model.mapper.MovieMapper.toDomain
import woowacourse.movie.model.mapper.TicketsMapper.toUi
import woowacourse.movie.sql.ReservationDbHelperInterface
import woowacourse.movie.view.SeatView
import java.time.LocalDateTime

class SelectSeatPresenter(
    val view: SelectSeatContract.View,
    peopleCount: Int,
    date: LocalDateTime,
    val movieUiModel: MovieUiModel,
    val reservationDbHelper: ReservationDbHelperInterface
) : SelectSeatContract.Presenter {
    private val ticketOffice =
        TicketOffice(Tickets(listOf()), DisCountPolicies(), date, peopleCount)

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
