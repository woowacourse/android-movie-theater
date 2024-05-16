package woowacourse.movie.presentation.view.reservation.seat

import woowacourse.movie.data.repository.ReservationMovieInfoRepositoryImpl
import woowacourse.movie.data.repository.SeatRepositoryImpl
import woowacourse.movie.domain.model.reservation.MovieTicket
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.domain.model.reservation.toReservationTicketEntity
import woowacourse.movie.presentation.repository.SeatRepository
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import woowacourse.movie.repository.db.ReservationTicketDao
import java.time.format.DateTimeFormatter

class SeatSelectionPresenterImpl(
    reservationCount: Int,
    seatRepository: SeatRepository = SeatRepositoryImpl,
    private val ticketDao: ReservationTicketDao,
) : SeatSelectionContract.Presenter {
    private var view: SeatSelectionContract.View? = null
    private val reservationInfo =
        ReservationInfo(reservationCount, seatRepository.getSeatingChart())
    private val reservationMovieInfoRepository = ReservationMovieInfoRepositoryImpl

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
        val id = storeData(ticket)
        view?.moveToReservationResult(movieTicketUiModel)
    }

    private fun makeTicket(): MovieTicket {
        return MovieTicket(
            0,
            reservationMovieInfo = reservationMovieInfoRepository.getScreeningMovieInfo(),
            reservationInfo = reservationInfo,
        )
    }

    private fun storeData(ticket: MovieTicket): Long {
        var ticketId = 0L
        val thread = Thread {
            ticketId = saveTicket(ticket)
        }

        thread.start()
        thread.join()
        return ticketId
    }


    private fun saveTicket(ticket: MovieTicket): Long {
        return ticketDao.saveReservationTicket(
            ticket.toReservationTicketEntity(
                selectDate =
                ticket.reservationMovieInfo.dateTime.screeningDate.date.format(
                    DateTimeFormatter.ISO_LOCAL_DATE,
                ),
                screenTime =
                ticket.reservationMovieInfo.dateTime.screeningDate.screeningTime.startTime.format(
                    DateTimeFormatter.ofPattern("HH:mm"),
                ),
            )
        )
    }
}
