package woowacourse.movie.presentation.view.reservation.seat

import woowacourse.movie.data.repository.ReservationMovieInfoRepositoryImpl
import woowacourse.movie.data.repository.SeatRepositoryImpl
import woowacourse.movie.database.Reservation
import woowacourse.movie.database.ReservationDao
import woowacourse.movie.domain.model.reservation.MovieTicket
import woowacourse.movie.domain.model.reservation.ReservationInfo
import woowacourse.movie.presentation.repository.SeatRepository
import woowacourse.movie.presentation.uimodel.MovieTicketUiModel
import kotlin.concurrent.thread

class SeatSelectionPresenterImpl(
    reservationCount: Int,
    private val reservationDao: ReservationDao,
    seatRepository: SeatRepository = SeatRepositoryImpl,
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
        val movieTicketUiModel =
            MovieTicketUiModel(
                MovieTicket(
                    0,
                    reservationMovieInfoRepository.getScreeningMovieInfo()!!,
                    reservationInfo,
                ),
            )
        val reservation =
            Reservation(
                title = movieTicketUiModel.title,
                screeningDate = movieTicketUiModel.screeningDate,
                startTime = movieTicketUiModel.startTime,
                endTime = movieTicketUiModel.endTime,
                runningTime = movieTicketUiModel.runningTime,
                count = movieTicketUiModel.reservationCount,
                theater = movieTicketUiModel.theaterName,
                seats = movieTicketUiModel.selectedSeats,
                totalPrice = movieTicketUiModel.totalPrice,
            )
        thread(start = true) { reservationDao.insert(reservation) }.join()
        view?.moveToReservationResult(movieTicketUiModel)
    }
}
