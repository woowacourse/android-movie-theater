package woowacourse.movie.presentation.view.navigation

import woowacourse.movie.domain.model.reservation.seat.Seat
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.presentation.view.reservation.seat.SeatSelectionActivity
import woowacourse.movie.repository.db.ReservationTicketDao
import woowacourse.movie.repository.db.toTicketUiModel

class ReservationListPresenterImpl(
    private val ticketDao: ReservationTicketDao,
    private val view: ReservationListContract.View
) :
    ReservationListContract.Presenter {

    override fun loadReservationTickets() {
        Thread {
            val tickets = ticketDao.findAllReservation()

            val movieTickets = tickets.map { ticketEntity ->
                ticketEntity.toTicketUiModel()
            }
            view.showReservationTickets(movieTickets)
        }.start()
    }

    override fun ticketInfo(ticketId: Long) {
        Thread {
            val ticket = ticketDao.findReservationById(ticketId)
            ticket?.let {
                val seat = seatsJoinToString(ticket.seats.seats)
                val ticketUiModel = ticket.toTicketUiModel()
                val movieTicketUiModel = MovieTicketUiModel(
                    ticketId = ticketUiModel.ticketId,
                    screeningDate = ticketUiModel.screeningDate,
                    title = ticketUiModel.title,
                    startTime = ticketUiModel.startTime,
                    endTime = "",
                    runningTime = 0,
                    reservationCount = ticket.seats.seats.size,
                    totalPrice = ticket.seats.totalPrice(),
                    selectedSeats = seat,
                    theaterName = ticketUiModel.theaterName,
                )
                view.moveToReservationResult(movieTicketUiModel)
            }
        }.start()
    }

    private fun seatsJoinToString(seats: List<Seat>): List<String> {
        val formattedSeats = mutableListOf<String>()
        seats.forEach { seat ->
            val formattedSeat = String.format(
                SeatSelectionActivity.SEAT_POSITION_TEXT_FORMAT,
                SeatSelectionActivity.SEAT_ROW_START_VALUE + seat.row,
                SeatSelectionActivity.SEAT_COL_START_VALUE + seat.col,
            )
            formattedSeats.add(formattedSeat)
        }
        return formattedSeats
    }
}
