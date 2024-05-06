package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatBoard
import woowacourse.movie.domain.model.SeatModel
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.MessageType.ReservationSuccessMessage
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.UserSeat

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
            seatSelectionModel.copy(
                dateTime = reservationInfo.dateTime,
                ticketQuantity = reservationInfo.ticketQuantity,
            )
        loadScreen(reservationInfo.theaterId, movieId)
        loadSeatBoard(reservationInfo.theaterId)
    }

    private fun loadScreen(
        theaterId: Int,
        movieId: Int,
    ) {
        repository.findScreen(theaterId = theaterId, movieId = movieId).onSuccess { screen ->
            updateScreen(theaterId, screen)
        }.onFailure { e ->
            onLoadError(e)
        }
    }

    private fun updateScreen(
        theaterId: Int,
        screen: Screen,
    ) {
        _seatSelectionModel =
            seatSelectionModel.copy(
                id = theaterId,
                screen = screen,
            )
    }

    private fun loadSeatBoard(id: Int) {
        repository.loadSeatBoard(id)
            .onSuccess { seatBoard ->
                updateSeatBoard(seatBoard)
                view.showSeatModel(seatSelectionModel)
            }
            .onFailure { e -> onLoadError(e) }
    }

    private fun updateSeatBoard(seatBoard: SeatBoard) {
        val userSeats =
            seatBoard.seats.map { seat ->
                SeatModel(seat.column, seat.row, seat.seatRank)
            }
        _seatSelectionModel = seatSelectionModel.copy(userSeat = UserSeat(userSeats))
    }

    private fun onLoadError(e: Throwable) {
        when (e) {
            is NoSuchElementException -> {
                view.showToastMessage(e)
                view.back()
            }

            else -> {
                view.showToastMessage(e)
                view.back()
            }
        }
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
                reservationRepository.saveReservation(
                    screen.movie,
                    seatSelectionModel.id,
                    seatSelectionModel.ticketQuantity,
                    seatSelectionModel.userSeat.seatModels.filter { it.isSelected }
                        .map { Seat(it.column, it.row, it.seatRank) },
                    dateTime,
                ).onSuccess { reservationId ->
                    view.showToastMessage(ReservationSuccessMessage)
                    view.navigateToReservation(reservationId)
                }.onFailure { e ->
                    view.showSnackBar(e)
                    view.back()
                }
            }
        }
    }
}
