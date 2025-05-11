package woowacourse.movie.view.reservation.seat

import android.os.Bundle
import woowacourse.movie.data.TicketRepositoryImpl
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.TicketRepository
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seat
import woowacourse.movie.domain.movieseat.Seats
import woowacourse.movie.view.reservation.toUi
import kotlin.concurrent.thread

class ReservationSeatPresenter(
    val view: ReservationSeatContract.View,
    private val ticketRepository: TicketRepository = TicketRepositoryImpl(),
) : ReservationSeatContract.Present {
    private var seats = Seats(mutableSetOf())
    private lateinit var ticket: Ticket

    override fun fetchData(ticket: Ticket) {
        this.ticket = ticket
        view.showMovieName(ticket.title)
        updateMoney()
    }

    override fun selectSeat(position: Position) {
        if (seats.selectedLimit(ticket.personnel).not()) {
            seats.addSeat(Seat(position))
            view.selectSeatView(position)
            updateMoney()
            canSelectedButton()
        }
    }

    override fun deselectSeat(position: Position) {
        seats.removeSeat(Seat(position))
        view.deselectSeatView(position)
        updateMoney()
        canSelectedButton()
    }

    override fun onSaveState(outState: Bundle) {
        outState.putSerializable(KEY_SEATS, seats)
    }

    override fun onRestoreState(outState: Bundle) {
        seats = outState.getSerializable(KEY_SEATS) as Seats
        updateMoney()
        seats.all.forEach { seat ->
            view.selectSeatView(seat.position)
        }
        canSelectedButton()
    }

    override fun createTicket() {
        this.ticket.seats.addSeat(seats)
        saveTicket()
        view.handleReservationComplete(ticket.toUi())
    }

    override fun updateMoney() {
        view.showTicketMoney(seats.reservationPrice())
    }

    private fun canSelectedButton() {
        view.setButton(seats.canSelect(ticket.personnel))
    }

    private fun saveTicket() {
        thread {
            ticketRepository.insertAll(ticket)
        }
    }

    companion object {
        const val KEY_SEATS = "seats"
    }
}
