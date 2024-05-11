package woowacourse.movie.ui.selection

import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.domain.Seat
import woowacourse.movie.domain.SeatInformation
import woowacourse.movie.domain.UserTicket
import woowacourse.movie.domain.mapper.toTicketEntity
import woowacourse.movie.ui.reservation.ReservationDetail
import woowacourse.movie.ui.utils.positionToIndex
import kotlin.concurrent.thread

class MovieSeatSelectionPresenter(
    private val view: MovieSeatSelectionContract.View,
    private val userTicketDataSource: TicketDao,
) :
    MovieSeatSelectionContract.Presenter {
    private lateinit var userTicket: UserTicket

    override fun loadTheaterInfo(reservationDetail: ReservationDetail) {
        userTicket =
            UserTicket(
                title = reservationDetail.title,
                theater = reservationDetail.theater,
                screeningStartDateTime = reservationDetail.screeningDateTime,
                seatInformation = SeatInformation(reservationCount = reservationDetail.count),
            )
        view.showReservationTotalAmount(userTicket.seatInformation.totalSeatAmount())
        view.showTheater(Seat.ROW_LEN, Seat.COL_LEN)
        view.showMovieTitle(userTicket.title)
        view.updateSelectCompletion(userTicket.seatInformation.checkSelectCompletion())
    }

    override fun updateSelectCompletion() {
        view.updateSelectCompletion(userTicket.seatInformation.checkSelectCompletion())
    }

    override fun selectSeat(
        row: Int,
        col: Int,
    ) {
        if (userTicket.seatInformation.selectedSeat.contains(Seat(row, col))) {
            unSelectingWork(row, col)
        } else {
            selectingWork(row, col)
        }
        view.updateSelectCompletion(userTicket.seatInformation.checkSelectCompletion())
        view.showReservationTotalAmount(userTicket.seatInformation.totalSeatAmount())
    }

    override fun recoverSeatSelection(index: Int) {
        view.showSelectedSeat(index)
    }

    override fun completeReservation() {
        thread {
            val ticketId = userTicketDataSource.save(userTicket.toTicketEntity())
            view.setAlarm(ticketId, userTicket.screeningStartDateTime, userTicket.title)
            view.navigateToCompleteScreen(ticketId)
        }
    }

    override fun handleError(throwable: Throwable) {
        view.showError(throwable)
    }

    private fun selectingWork(
        row: Int,
        col: Int,
    ) {
        if (userTicket.seatInformation.addSeat(row, col)) {
            view.showSelectedSeat(positionToIndex(row, col))
        }
    }

    private fun unSelectingWork(
        row: Int,
        col: Int,
    ) {
        userTicket.seatInformation.removeSeat(row, col)
        view.showUnSelectedSeat(positionToIndex(row, col))
    }
}
