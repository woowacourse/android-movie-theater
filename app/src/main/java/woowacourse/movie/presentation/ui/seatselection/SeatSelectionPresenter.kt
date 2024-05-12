package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.UserSeat
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val repository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
) : SeatSelectionContract.Presenter {
    private var _seatSelectionModel: SeatSelectionUiModel = SeatSelectionUiModel()
    val seatSelectionModel: SeatSelectionUiModel
        get() = _seatSelectionModel

    override fun updateUiModel(
        reservationInfo: ReservationInfo,
        movieId: Int,
    ) {
        _seatSelectionModel =
            seatSelectionModel.copyWithReservationInfo(reservationInfo)
        loadScreen(reservationInfo.theaterId, movieId)
        loadSeatBoard(reservationInfo.theaterId)
    }

    private fun loadScreen(
        theaterId: Int,
        movieId: Int,
    ) {
        repository.findScreen(theaterId = theaterId, movieId = movieId)
            .onSuccess { screen ->
                repository.findTheaterNameById(theaterId).onSuccess {
                    updateScreen(it, screen)
                }
            }
            .onFailure { e -> view.terminateOnError(e) }
    }

    private fun updateScreen(
        theaterName: String,
        screen: Screen,
    ) {
        _seatSelectionModel =
            seatSelectionModel.copy(
                theaterName = theaterName,
                screen = screen,
            )
    }

    private fun loadSeatBoard(id: Int) {
        repository.loadSeatBoard(id)
            .onSuccess { seatBoard ->
                updateSeatBoard(seatBoard)
                view.showSeatModel(seatSelectionModel)
            }
            .onFailure { e -> view.terminateOnError(e) }
    }

    private fun updateSeatBoard(seatBoard: SeatBoard) {
        val userSeats =
            seatBoard.seats.map { seat ->
                SeatModel(seat.column, seat.row, seat.seatRank)
            }
        _seatSelectionModel = seatSelectionModel.copy(userSeat = UserSeat(userSeats))
    }

    override fun clickSeat(seatModel: SeatModel) {
        if (seatModel.isSelected) {
            seatModel.isSelected = false
            calculateSeat()
            return
        }
        if (seatSelectionModel.userSeat.seatModels.count { it.isSelected } == seatSelectionModel.ticketQuantity) {
            view.showSnackBar(MessageType.AllSeatsSelectedMessage(seatSelectionModel.ticketQuantity))
        } else {
            seatModel.isSelected = true
            calculateSeat()
        }
    }

    override fun calculateSeat() {
        val newPrice =
            seatSelectionModel.userSeat.seatModels.filter { it.isSelected }.sumOf {
                it.seatRank.price
            }
        _seatSelectionModel = seatSelectionModel.copy(totalPrice = newPrice)
        view.showSeatModel(seatSelectionModel)
    }

    override fun reserve() {
        seatSelectionModel.screen?.let { screen ->
            seatSelectionModel.dateTime?.let { dateTime ->
                val reservation = seatSelectionModel.createReservation(screen, dateTime)
                saveReservation(reservation)
            }
        }
    }

    private fun saveReservation(reservation: Reservation) {
        thread {
            reservationRepository.saveReservation(reservation).onSuccess { reservationId ->
                view.setReservationNotification(reservation.copy(id = reservationId))
                view.navigateToReservation(reservationId)
            }.onFailure { e ->
                view.terminateOnError(e)
            }
        }
    }
}
