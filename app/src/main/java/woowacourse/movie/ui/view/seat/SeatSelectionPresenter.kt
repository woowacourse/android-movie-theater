package woowacourse.movie.ui.view.seat

import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Ticket
import woowacourse.movie.ui.view.data.TicketDataAdapter
import java.util.concurrent.atomic.AtomicReference
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val ticket: Ticket,
    private val ticketDataAdapter: TicketDataAdapter,
    selectedSeats: Set<Seat>?,
) : SeatSelectionContract.Presenter {
    private val seats: Set<Seat> = Seat.seats()
    private var selectedSeats = selectedSeats?.toSet() ?: emptySet()
    private val completable get() = ticket.count == selectedSeats.size
    private val price: Int get() = selectedSeats.sumOf(Seat::price)

    override fun presentSeats() {
        view.setSeats(seats, selectedSeats)
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
        if (seat in selectedSeats) {
            selectedSeats -= seat
        } else {
            if (canSelectSeat()) {
                selectedSeats += seat
            }
        }

        view.setSeatIsSelected(seat, seat in selectedSeats)
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
            view.saveTicket(title, count, showtime, seats, cinemaName)
        }
    }

    private fun canSelectSeat(): Boolean = selectedSeats.size < ticket.count

    override fun getSelectedSeats(): Set<Seat> = selectedSeats.toSet()
}
