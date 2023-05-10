package woowacourse.movie.view.seatselect

import domain.Reservation
import woowacourse.movie.database.ReservationDbHelperInterface
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.SeatUiModel
import woowacourse.movie.model.TicketOfficeUiModel
import woowacourse.movie.model.mapper.MovieMapper.toDomain
import woowacourse.movie.model.mapper.SeatMapper.toDomain
import woowacourse.movie.model.mapper.SeatRankMapper.toUi
import woowacourse.movie.model.mapper.TicketOfficeMapper.toDomain
import woowacourse.movie.model.mapper.TicketsMapper.toUi

class SelectSeatPresenter(
    val view: SelectSeatContract.View,
    val ticketOfficeUiModel: TicketOfficeUiModel,
    val movieUiModel: MovieUiModel,
    val reservationDbHelper: ReservationDbHelperInterface,
) : SelectSeatContract.Presenter {
    private val ticketOffice = ticketOfficeUiModel.toDomain()

    override fun updateSeatsRank(seatUiModels: List<SeatUiModel>) {
        val seats = seatUiModels.map { it.toDomain() }
        val ranksUiModels = seats.map { it.rank.toUi() }
        view.setSeatsTextColor(ranksUiModels)
    }

    override fun updateTickets(seatUiModel: SeatUiModel) {
        val seat = seatUiModel.toDomain()
        val ticket = ticketOffice.generateTicket(
            seat.row,
            seat.col,
        )
        ticketOffice.updateTickets(ticket)
        view.setSeatsBackgroundColor(ticketOffice.tickets.toUi())
    }

    override fun calculatePrice() {
        val price = ticketOffice.tickets.price
        view.setPriceText(price.value)
    }

    override fun changeButtonState() {
        val canAddTicket = !ticketOffice.isAvailableAddTicket()
        view.setCheckButtonClickable(canAddTicket)
        view.setCheckButtonColorBy(canAddTicket)
    }

    override fun completeReservation() {
        view.askConfirmReservation()
    }

    override fun showResult() {
        view.showResultScreen(movieUiModel, ticketOffice.tickets.toUi())
    }

    override fun registerAlarm() {
        view.setAlarm(movieUiModel, ticketOffice.tickets.toUi())
    }

    override fun saveReservation() {
        val reservation = Reservation(movieUiModel.toDomain(), ticketOffice.tickets)
        reservationDbHelper.saveReservation(reservation)
    }
}
