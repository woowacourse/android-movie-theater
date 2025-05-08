package woowacourse.movie.seat

import android.content.Context
import woowacourse.movie.data.database.ReservationDatabase
import woowacourse.movie.data.entity.Reservation
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.model.Seat
import woowacourse.movie.model.Seats
import woowacourse.movie.model.Ticket
import woowacourse.movie.ui.model.SeatUiModel
import woowacourse.movie.ui.model.TicketUiModel
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
) : SeatSelectionContract.Presenter {
    private lateinit var ticket: Ticket

    override fun initializeData(ticket: TicketUiModel) {
        this.ticket = ticket.toDomain()
        view.showTicket(ticket)
    }

    override fun updateSeats(
        row: Int,
        col: Int,
    ) {
        val seat = Seat(row, col)

        ticket =
            if (ticket.hasSeat(seat)) {
                ticket.unselectSeat(seat)
            } else {
                if (ticket.canReserve()) return
                ticket.selectSeat(seat)
            }

        view.showSeatState(seat.toUiModel(), ticket.hasSeat(seat))

        updateBookingState()
    }

    private fun updateBookingState() {
        val shouldEnableButton = ticket.canReserve()
        view.updateCanBook(shouldEnableButton)

        view.showTicket(ticket.toUiModel())
    }

    override fun completeBooking() {
        val ticketUiModel = ticket.toUiModel()
        view.showBookingAlertDialog(ticketUiModel)
    }

    override fun storeSeats(context: Context) {
        val db = ReservationDatabase.getDatabase(context)
        thread {
            db.reservationDao().saveReservation(
                Reservation(
                    title = ticket.title,
                    date = ticket.selectedDate.toString(),
                    time = ticket.selectedTime.toString(),
                    headCount = ticket.headCount.value,
                    seat = convertSeat(),
                    theater = ticket.theater,
                    price = ticket.amount.toString(),
                ),
            )
        }
    }

    private fun convertSeat(): String {
        return ticket.seats.seats.joinToString(", ") { point ->
            "${'A' + point.row}${point.col + 1}"
        }
    }

    override fun restoreSeats(selectedSeats: List<SeatUiModel>) {
        ticket = ticket.copy(seats = Seats(selectedSeats.map { it.toDomain() }.toSet()))
        view.showTicket(ticket.toUiModel())
        view.updateCanBook(ticket.canReserve())
    }
}
