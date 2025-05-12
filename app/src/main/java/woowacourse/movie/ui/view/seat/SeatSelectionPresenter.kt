package woowacourse.movie.ui.view.seat

import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.data.TicketDataAdapter
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private var ticket: Ticket,
    private val ticketDataAdapter: TicketDataAdapter,
    selectedSeats: Set<Seat>?,
) : SeatSelectionContract.Presenter {
    private val seats: Set<Seat> = Seat.seats()
    private val completable get() = ticket.count == ticket.seats.size
    private val price: Int get() = ticket.seats.sumOf(Seat::price)

    init {
        ticket = ticket.updateSeats(selectedSeats ?: emptySet())
    }

    override fun presentSeats() {
        view.setSeats(seats, ticket.seats)
    }

    override fun presentTitle() {
        view.setTitle(ticket.title)
    }

    override fun presentPrice() {
        view.setPrice(price)
    }

    override fun presentCompleteButton() {
        view.setConfirmEnabled(completable)
    }

    override fun onSeatSelect(seat: Seat) {
        if (seat in ticket.seats) {
            ticket = ticket.removeSeat(seat)
        } else {
            if (canSelectSeat()) {
                ticket = ticket.addSeat(seat)
            }
        }

        view.setSeatIsSelected(seat, seat in ticket.seats)
        view.setPrice(price)
        view.setConfirmEnabled(completable)
    }

    override fun tryReservation() {
        view.askFinalReservation()
    }

    override fun confirmReservation() {
        val insertTicket = AtomicReference<Ticket>()
        val thread =
            thread {
                val insertTicketId = ticketDataAdapter.insert(ticket)
                val ticket = ticketDataAdapter.getTicket(insertTicketId)
                insertTicket.set(ticket)
            }
        thread.join()
        insertTicket.get().run {
            view.setTicketAlarm(this)
            view.saveTicket(title, count, showtime, seats, cinemaName, purchaseType)
        }
    }

    private fun canSelectSeat(): Boolean = ticket.seats.size < ticket.count

    override fun getSelectedSeats(): Set<Seat> = ticket.seats.toSet()
}
