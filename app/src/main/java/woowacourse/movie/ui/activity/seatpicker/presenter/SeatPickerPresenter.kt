package woowacourse.movie.ui.activity.seatpicker.presenter

import woowacourse.movie.data.entity.Reservations
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.domain.MovieTicket
import woowacourse.movie.ui.activity.seatpicker.SeatPickerContract
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.mapToMovieTicket
import woowacourse.movie.ui.model.mapToMovieTicketModel
import woowacourse.movie.ui.model.mapToMovieTicketModelWithOriginalPrice
import woowacourse.movie.ui.model.seat.SeatModel
import woowacourse.movie.ui.model.seat.mapToSeat

class SeatPickerPresenter(
    private val reservationRepository: ReservationRepository,
    private val view: SeatPickerContract.View
) : SeatPickerContract.Presenter {
    private lateinit var ticket: MovieTicket

    override fun initTicket(ticketModel: MovieTicketModel) {
        ticket = ticketModel.mapToMovieTicket()
        view.applyTicketData(ticket.mapToMovieTicketModel())
    }

    override fun handleSeatSelection(isReserved: Boolean, seat: SeatModel) {
        if (isReserved) {
            cancelSeat(seat)
            return
        }
        reserveSeat(seat)
    }

    private fun reserveSeat(seat: SeatModel) {
        if (ticket.canReserveSeat()) {
            addSeat(seat)
            return
        }
        view.notifyUnableToReserveMore()
    }

    private fun addSeat(seat: SeatModel) {
        ticket.reserveSeat(seat.mapToSeat())
        val price = ticket.getDiscountPrice()
        view.setSeatReserved(seat)
        view.updatePrice(price.amount)
    }

    private fun cancelSeat(seat: SeatModel) {
        ticket.cancelSeat(seat.mapToSeat())
        val price = ticket.getDiscountPrice()
        view.setSeatCanceled(seat)
        view.updatePrice(price.amount)
    }

    override fun checkSelectionDone() {
        if (ticket.canReserveSeat()) {
            view.deactivateDoneButton()
        } else {
            view.activateDoneButton()
        }
    }

    override fun completeReservation() {
        val ticketModel = ticket.mapToMovieTicketModel()
        addReservation(ticketModel)
        view.afterReservation(ticketModel)
    }

    override fun getTicketForSaving(): MovieTicketModel {
        return ticket.mapToMovieTicketModelWithOriginalPrice()
    }

    private fun addReservation(ticketModel: MovieTicketModel) {
        Reservations.addItem(ticketModel)
        reservationRepository.insertReservation(ticketModel)
    }
}
