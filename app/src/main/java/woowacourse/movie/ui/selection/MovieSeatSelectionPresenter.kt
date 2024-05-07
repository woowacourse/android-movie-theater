package woowacourse.movie.ui.selection

import woowacourse.movie.model.data.MovieDataSource
import woowacourse.movie.model.movie.Reservation
import woowacourse.movie.model.movie.ReservationDetail
import woowacourse.movie.model.movie.Seat
import woowacourse.movie.model.movie.UserTicket
import woowacourse.movie.ui.utils.positionToIndex

class MovieSeatSelectionPresenter(
    private val view: MovieSeatSelectionContract.View,
    private val reservations: MovieDataSource<Reservation>,
    private val userTickets: MovieDataSource<UserTicket>,
) :
    MovieSeatSelectionContract.Presenter {
    private lateinit var reservation: Reservation
    private lateinit var reservationDetail: ReservationDetail

    override fun loadTheaterInfo(reservationId: Long) {
        try {
            reservation = reservations.find(reservationId)
            reservationDetail = ReservationDetail(reservation.reservationCount.count)
            view.showMovieTitle(reservation.title)
            view.showReservationTotalAmount(reservationDetail.totalSeatAmount())
            view.showTheater(Seat.ROW_LEN, Seat.COL_LEN)
        } catch (e: NoSuchElementException) {
            view.showError(e)
        }
    }

    override fun updateSelectCompletion() {
        view.updateSelectCompletion(reservationDetail.checkSelectCompletion())
    }

    override fun selectSeat(
        row: Int,
        col: Int,
    ) {
        if (reservationDetail.selectedSeat.contains(Seat(row, col))) {
            unSelectingWork(row, col)
        } else {
            selectingWork(row, col)
        }
        view.updateSelectCompletion(reservationDetail.checkSelectCompletion())
        view.showReservationTotalAmount(reservationDetail.totalSeatAmount())
    }

    override fun recoverSeatSelection(index: Int) {
        view.showSelectedSeat(index)
    }

    override fun reservationSeat() {
        val userTicket = UserTicket(
            movieTitle = reservation.title,
            screeningStartDateTime = reservation.screeningStartDateTime,
            reservationCount = reservationDetail.reservationCount,
            reservationSeats = reservationDetail.selectedSeat,
            theaterName = reservation.theater,
            reservationAmount = reservationDetail.totalSeatAmount(),
        )
        val userTicketId = userTickets.save(userTicket)
        view.showSeatReservationConfirmation(userTicketId)
    }

    private fun selectingWork(
        row: Int,
        col: Int,
    ) {
        if (reservationDetail.addSeat(row, col)) {
            view.showSelectedSeat(positionToIndex(row, col))
        }
    }

    private fun unSelectingWork(
        row: Int,
        col: Int,
    ) {
        reservationDetail.removeSeat(row, col)
        view.showUnSelectedSeat(positionToIndex(row, col))
    }
}
