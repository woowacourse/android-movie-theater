package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.Seat
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

    override fun updateUiModel(reservationInfo: ReservationInfo) {
        _seatSelectionModel =
            seatSelectionModel.copy(
                id = reservationInfo.theaterId,
                dateTime = reservationInfo.dateTime,
                ticketCount = reservationInfo.ticketQuantity,
            )
    }

    override fun loadScreen(id: Int) {
        repository.findByScreenId(theaterId = id, movieId = id).onSuccess { screen ->
            _seatSelectionModel = seatSelectionModel.copy(screen = screen)
        }.onFailure { e ->
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
    }

    override fun loadSeatBoard(id: Int) {
        repository.loadSeatBoard(id).onSuccess { seatBoard ->
            _seatSelectionModel =
                _seatSelectionModel.copy(
                    userSeat =
                        UserSeat(
                            seatBoard.seats.map {
                                SeatModel(
                                    it.column,
                                    it.row,
                                    it.seatRank,
                                )
                            },
                        ),
                )
        }.onFailure { e ->
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
    }

    override fun clickSeat(seatModel: SeatModel) {
        if (seatModel.isSelected) {
            seatModel.isSelected = false
            calculateSeat()
            return
        }
        if (seatSelectionModel.userSeat.seatModels.count { it.isSelected } == seatSelectionModel.ticketCount) {
            view.showSnackBar(MessageType.AllSeatsSelectedMessage(seatSelectionModel.ticketCount))
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
        view.showTotalPrice(seatSelectionModel.totalPrice)
    }

    override fun showConfirmDialog() {
        view.showReservationDialog()
    }

    override fun reserve() {
        seatSelectionModel.screen?.let { screen ->
            seatSelectionModel.dateTime?.let { dateTime ->
                reservationRepository.saveReservation(
                    screen.movie,
                    seatSelectionModel.id,
                    seatSelectionModel.ticketCount,
                    seatSelectionModel.userSeat.seatModels.filter { it.isSelected }
                        .map { Seat(it.column, it.row, it.seatRank) },
                    dateTime,
                ).onSuccess { id ->
                    view.showToastMessage(ReservationSuccessMessage)
                    view.navigateToReservation(id)
                }.onFailure { e ->
                    view.showSnackBar(e)
                    view.back()
                }
            }
        }
    }
}
