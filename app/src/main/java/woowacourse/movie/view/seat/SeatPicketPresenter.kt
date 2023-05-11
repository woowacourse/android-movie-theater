package woowacourse.movie.view.seat

import woowacourse.movie.db.ReservationDataImpl
import woowacourse.movie.domain.MovieTicket
import woowacourse.movie.entity.Reservations
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.mapToMovieTicket
import woowacourse.movie.model.mapToMovieTicketModel
import woowacourse.movie.model.mapToMovieTicketModelWithOriginalPrice
import woowacourse.movie.model.mapToPriceModel
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.model.seat.mapToSeat

class SeatPicketPresenter(
    private val view: SeatPickerContract.View,
    private val reservationDataImpl: ReservationDataImpl
) :
    SeatPickerContract.Presenter {
    private lateinit var ticket: MovieTicket

    override fun setupTicket(ticketModel: MovieTicketModel) {
        ticket = ticketModel.mapToMovieTicket()
        view.setTicketViews(ticket.mapToMovieTicketModel())
        view.setEnabledDone(!ticket.canReserveSeat())
    }

    override fun setSelectedSeat(seat: SeatModel) {
        view.setSelectedSeat(seat, ticket.mapToMovieTicketModel().isSelectedSeat(seat))
    }

    override fun addSeat(seat: SeatModel) {
        ticket.reserveSeat(seat.mapToSeat())
        view.setSelectedSeat(seat, ticket.mapToMovieTicketModel().isSelectedSeat(seat))
        view.setTextPrice(ticket.getDiscountPrice().mapToPriceModel())
    }

    override fun cancelSeat(seat: SeatModel) {
        ticket.cancelSeat(seat.mapToSeat())
        view.setSelectedSeat(seat, ticket.mapToMovieTicketModel().isSelectedSeat(seat))
        view.setTextPrice(ticket.getDiscountPrice().mapToPriceModel())
    }

    override fun canReserveSeat(): Boolean {
        val isEnabled = ticket.canReserveSeat()
        view.setEnabledDone(!isEnabled)
        return isEnabled
    }

    override fun actionReservation() {
        view.showReservationCheckDialog(ticket.mapToMovieTicketModel())
    }

    override fun saveReservation(ticketModel: MovieTicketModel) {
        Reservations.addItem(ticketModel)
        reservationDataImpl.insertReservation(ticketModel)
    }

    override fun getTicketOriginalModel(): MovieTicketModel =
        ticket.mapToMovieTicketModelWithOriginalPrice()
}
