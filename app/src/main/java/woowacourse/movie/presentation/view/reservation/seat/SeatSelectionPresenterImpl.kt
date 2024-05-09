package woowacourse.movie.presentation.view.reservation.seat

import android.util.Log
import woowacourse.movie.data.repository.ReservationMovieInfoRepositoryImpl
import woowacourse.movie.data.repository.SeatRepositoryImpl
import woowacourse.movie.domain.model.reservation.MovieTicket
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.domain.model.reservation.toReservationTicketEntity
import woowacourse.movie.presentation.repository.SeatRepository
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.repository.db.ReservationTicketDao

class SeatSelectionPresenterImpl(
    reservationCount: Int,
    seatRepository: SeatRepository = SeatRepositoryImpl,
    private val ticketDao: ReservationTicketDao,
) : SeatSelectionContract.Presenter {
    private var view: SeatSelectionContract.View? = null
    private val reservationInfo =
        ReservationInfo(reservationCount, seatRepository.getSeatingChart())
    private val reservationMovieInfoRepository = ReservationMovieInfoRepositoryImpl

    fun makeTicket(): MovieTicket {
        val ticket = MovieTicket(
            0,
            reservationMovieInfo = reservationMovieInfoRepository.getScreeningMovieInfo()!!,
            reservationInfo = reservationInfo
        )
        return ticket
    }

    fun storeData(): Long {
        var ticketId = 0L
        Log.d("ticket", "Before :storeData")
        val ticket = makeTicket()

        val thread = Thread {
            Log.d("ticket", "ticektId :${ticket.ticketId}")
            ticketId = ticketDao.saveReservationTicket(
                ticket.toReservationTicketEntity(
                    ticket.reservationMovieInfo.title,
                    ticket.reservationMovieInfo.theaterName
                )
            )
        }

        thread.start()
        thread.join()

        Log.d("ticket", "After: storeData")

        return ticketId
    }

    override fun attachView(view: SeatSelectionContract.View) {
        this.view = view
        onViewSetUp()
    }

    override fun detachView() {
        this.view = null
    }

    override fun onViewSetUp() {
        loadSeatingChart()
    }

    override fun loadSeatingChart() {
        view?.showSeatingChart(
            reservationInfo.seatingChart.rowCount,
            reservationInfo.seatingChart.colCount,
            reservationInfo.seatingChart.getSeatRankInfo(),
        )
    }

    override fun selectSeat(
        row: Int,
        col: Int,
    ) {
        when (reservationInfo.selectedSeats.tryAddOrDeleteSeat(row, col)) {
            true -> view?.changeSeatColor(row, col)
            false -> view?.showAlreadyFilledSeatsSelectionMessage()
        }
        view?.updateTotalPrice(reservationInfo.selectedSeats.totalPrice())
        view?.changeConfirmClickable(reservationInfo.selectedSeats.isMatchedTheCount())
    }

    override fun onAcceptButtonClicked() {
        val ticket = makeTicket()
        val movieTicketUiModel = MovieTicketUiModel(ticket)
        val id = storeData()
        view?.moveToReservationResult(movieTicketUiModel)
    }
}
