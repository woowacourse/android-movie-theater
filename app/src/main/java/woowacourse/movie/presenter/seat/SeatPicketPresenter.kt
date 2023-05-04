package woowacourse.movie.presenter.seat

import woowacourse.movie.contract.seat.SeatPickerContract
import woowacourse.movie.domain.MovieTicket
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.mapToMovieTicket
import woowacourse.movie.ui.model.mapToMovieTicketModel
import woowacourse.movie.ui.model.mapToMovieTicketModelWithOriginalPrice
import woowacourse.movie.ui.model.mapToPriceModel
import woowacourse.movie.ui.model.seat.SeatModel
import woowacourse.movie.ui.model.seat.mapToSeat

class SeatPicketPresenter(private val view: SeatPickerContract.View) :
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

    override fun getTicketOriginalModel(): MovieTicketModel =
        ticket.mapToMovieTicketModelWithOriginalPrice()
}
