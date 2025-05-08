package woowacourse.movie.view.reservation.seat

import woowacourse.movie.data.MovieTheaterDatabase
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatFactory
import woowacourse.movie.domain.model.Ticket
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
) : SeatSelectionContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo
    private lateinit var ticket: Ticket

    private val seatFactory = SeatFactory.default()
    private val seats = seatFactory.seats

    override fun loadSeats(reservationInfo: ReservationInfo) {
        this.reservationInfo = reservationInfo
        ticket = reservationInfo.toTicket(listOf())
        view.showSeats(seats, listOf())
        updateScreen()
    }

    override fun loadSeats(
        reservationInfo: ReservationInfo,
        ticket: Ticket,
    ) {
        this.ticket = ticket
        view.showSeats(seats, ticket.seats)
        updateScreen()
    }

    override fun selectSeat(seat: Seat) {
        runCatching {
            ticket = ticket.updateSeats(seat)
            view.showSeats(seats, ticket.seats)
            updateScreen()
        }.onFailure { e ->
            view.showError(e.message)
        }
    }

    private fun updateScreen() {
        view.updateTicketInfo(ticket)
    }

    override fun showConfirmButton() {
        view.showReservationDialog()
    }

    override fun completeReservation() {
        thread {
            val ticketDao = MovieTheaterDatabase.db.ticketDao()
            ticketDao.save(
                TicketEntity(
                    title = ticket.title,
                    showTime = ticket.showTime,
                    reservationCount = ticket.reservationCount,
                    cinemaName = ticket.cinema.name,
                    seats = ticket.seats,
                    totalPrice = ticket.totalPrice(),
                ),
            )
        }
        view.navigateToResult(ticket)
    }
}
