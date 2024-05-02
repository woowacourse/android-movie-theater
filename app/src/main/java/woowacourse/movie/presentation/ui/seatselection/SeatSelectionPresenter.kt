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
    private var _uiModel: SeatSelectionUiModel = SeatSelectionUiModel()
    val uiModel: SeatSelectionUiModel
        get() = _uiModel

    override fun updateUiModel(reservationInfo: ReservationInfo) {
        _uiModel =
            uiModel.copy(
                id = reservationInfo.theaterId,
                dateTime = reservationInfo.dateTime,
                ticketCount = reservationInfo.ticketCount,
            )
    }

    override fun loadScreen(id: Int) {
        repository.findByScreenId(theaterId = id, movieId = id).onSuccess { screen ->
            _uiModel = uiModel.copy(screen = screen)
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
            _uiModel =
                _uiModel.copy(
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
        if (uiModel.userSeat.seatModels.count { it.isSelected } == uiModel.ticketCount) {
            view.showSnackBar(MessageType.AllSeatsSelectedMessage(uiModel.ticketCount))
        } else {
            seatModel.isSelected = true
            calculateSeat()
        }
    }

    override fun calculateSeat() {
        val newPrice =
            uiModel.userSeat.seatModels.filter { it.isSelected }.sumOf {
                it.seatRank.price
            }
        _uiModel = uiModel.copy(totalPrice = newPrice)
        view.showTotalPrice(uiModel.totalPrice)
    }

    override fun showConfirmDialog() {
        view.showReservationDialog()
    }

    override fun reserve() {
        uiModel.screen?.let { screen ->
            uiModel.dateTime?.let { dateTime ->
                reservationRepository.saveReservation(
                    screen.movie,
                    uiModel.id,
                    uiModel.ticketCount,
                    uiModel.userSeat.seatModels.filter { it.isSelected }
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
