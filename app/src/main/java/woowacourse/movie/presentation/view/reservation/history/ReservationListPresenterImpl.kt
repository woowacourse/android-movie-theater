package woowacourse.movie.presentation.view.reservation.history

import woowacourse.movie.domain.model.reservation.seat.Seat
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity
import woowacourse.movie.repository.db.ReservationTicketDao
import woowacourse.movie.repository.db.toTicketUiModel
import kotlin.concurrent.thread

class ReservationListPresenterImpl(
    private val ticketDao: ReservationTicketDao,
    private val view: ReservationListContract.View,
) :
    ReservationListContract.Presenter {
    override fun loadReservationTickets() {
        thread {
            val tickets = ticketDao.findAllReservation()

            val movieTickets =
                tickets.map { ticketEntity ->
                    ticketEntity.toTicketUiModel()
                }
            view.showReservationTickets(movieTickets)
        }
    }

    override fun ticketInfo(ticketId: Long) {
        Thread {
            val ticket = ticketDao.findReservationById(ticketId)
            ticket?.let {
                val seat = seatsJoinToString(ticket.selectedSeats.seats)
                val ticketUiModel = ticket.toTicketUiModel()
                val movieTicketUiModel =
                    MovieTicketUiModel(
                        ticketId = ticketUiModel.ticketId,
                        screeningDate = ticketUiModel.screeningDate,
                        title = ticketUiModel.title,
                        startTime = ticketUiModel.startTime,
                        reservationCount = ticket.selectedSeats.seats.size,
                        totalPrice = ticket.selectedSeats.totalPrice(),
                        selectedSeats = seat,
                        theaterName = ticketUiModel.theaterName,
                    )
                view.moveToReservationResult(movieTicketUiModel)
            }
        }.start()
    }

    private fun seatsJoinToString(seats: List<Seat>): List<String> {
        return seats.map { seat ->
            String.format(
                SeatSelectionActivity.SEAT_POSITION_TEXT_FORMAT,
                SeatSelectionActivity.SEAT_ROW_START_VALUE + seat.row,
                SeatSelectionActivity.SEAT_COL_START_VALUE + seat.col,
            )
        }
    }
}
