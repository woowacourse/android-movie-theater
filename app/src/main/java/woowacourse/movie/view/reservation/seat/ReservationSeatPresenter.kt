package woowacourse.movie.view.reservation.seat

import android.content.Context
import android.util.Log
import woowacourse.movie.data.TicketInfoDatabase
import woowacourse.movie.data.toEntity
import woowacourse.movie.domain.Ticket
import woowacourse.movie.domain.movieseat.Position
import woowacourse.movie.domain.movieseat.Seat
import woowacourse.movie.domain.movieseat.Seats
import kotlin.concurrent.thread

class ReservationSeatPresenter(
    val view: ReservationSeatContract.View,
) : ReservationSeatContract.Present {
    private lateinit var ticket: Ticket
    private var seats = Seats()

    override fun fetchData(ticket: Ticket) {
        this.ticket = ticket
        view.setSeatTag()
        view.setSeatInit()
        view.showMovieName(ticket.title)
        view.setSeatClickListener()
        view.setReservationButton {
            view.showReservationDialog(ticket, seats)
        }
        refreshUI()
    }

    override fun selectSeat(position: Position) {
        if (seats.selectedLimit(ticket.personnel).not()) {
            seats = seats.addSeat(Seat(position))
            view.setSeat(seats)
            view.selectSeatView(position)
            refreshUI()
        }
    }

    override fun deselectSeat(position: Position) {
        seats = seats.removeSeat(Seat(position))
        view.setSeat(seats)
        view.deselectSeatView(position)
        refreshUI()
    }

    private fun refreshUI() {
        updateMoney()
        updateReservationBtnState()
    }

    override fun restoreSeat(seats: Seats) {
        this.seats = seats
        seats.selectedSeats.forEach { seat ->
            view.selectSeatView(seat.position)
        }
        refreshUI()
    }

    override fun updateMoney() {
        view.showTicketMoney(seats.reservationPrice())
    }

    private fun updateReservationBtnState() {
        if (seats.canSelect(ticket.personnel)) {
            view.selectableButton()
        } else {
            view.deSelectableButton()
        }
    }

    override fun saveTicketInfo(ticket: Ticket) {
        thread {
            val db = TicketInfoDatabase.getDatabase(view as Context)
            db.ticketInfoDao().insert(ticket.toEntity())

            val tickets = db.ticketInfoDao().getAll()
            Log.d("ticket", tickets.toString())
        }
    }

    companion object {
        const val KEY_SEATS = "seats"
    }
}
