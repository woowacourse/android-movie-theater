package woowacourse.movie.presentation.ui.seatselection

import android.os.Handler
import android.os.Looper
import woowacourse.movie.domain.model.toSeatModel
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.MessageType.ReservationSuccessMessage
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.UserSeat
import woowacourse.movie.presentation.model.toSeat
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
) : SeatSelectionContract.Presenter {
    private var uiModel: SeatSelectionUiModel = SeatSelectionUiModel()
    val userSeat: UserSeat
        get() = UserSeat(uiModel.userSeat.seatModels.filter { it.isSelected })

    override fun updateUiModel(reservationInfo: ReservationInfo) {
        uiModel =
            uiModel.copy(
                id = reservationInfo.theaterId,
                dateTime = reservationInfo.dateTime,
                ticketCount = reservationInfo.ticketCount,
            )
    }

    override fun loadScreen(
        theaterId: Int,
        movieId: Int,
    ) {
        screenRepository.findByScreenId(theaterId = theaterId, movieId = movieId)
            .onSuccess { screen ->
                uiModel = uiModel.copy(screen = screen)
                view.showScreen(screen, uiModel.totalPrice, uiModel.ticketCount)
            }.onFailure { e ->
                when (e) {
                    is NoSuchElementException -> {
                        view.showToastMessage(e)
                        view.navigateBackToPrevious()
                    }

                    else -> {
                        view.showToastMessage(e)
                        view.navigateBackToPrevious()
                    }
                }
            }
    }

    override fun loadSeatBoard(id: Int) {
        screenRepository.loadSeatBoard(id).onSuccess { seatBoard ->
            val seats = seatBoard.seats.map { seat -> seat.toSeatModel() }
            uiModel = uiModel.copy(userSeat = UserSeat(seats))
            view.showSeatBoard(uiModel.userSeat)
        }.onFailure { e ->
            when (e) {
                is NoSuchElementException -> {
                    view.showToastMessage(e)
                    view.navigateBackToPrevious()
                }

                else -> {
                    view.showToastMessage(e)
                    view.navigateBackToPrevious()
                }
            }
        }
    }

    override fun clickSeat(seatModel: SeatModel) {
        val updatedSeatModels =
            uiModel.userSeat.seatModels.map { seatModelItem ->
                if (seatModel == seatModelItem) {
                    seatModelItem.copy(isSelected = !seatModelItem.isSelected)
                } else {
                    seatModelItem
                }
            }

        if (seatModel.isSelected) {
            uiModel = uiModel.copy(userSeat = uiModel.userSeat.copy(seatModels = updatedSeatModels))
            view.unselectSeat(uiModel.userSeat)
            return
        }

        if (uiModel.userSeat.seatModels.count { it.isSelected } == uiModel.ticketCount) {
            view.showSnackBar(MessageType.AllSeatsSelectedMessage(uiModel.ticketCount))
        } else {
            uiModel = uiModel.copy(userSeat = uiModel.userSeat.copy(seatModels = updatedSeatModels))
            view.selectSeat(uiModel.userSeat)
        }
    }

    override fun calculateSeat() {
        val newPrice =
            uiModel.userSeat.seatModels.filter { it.isSelected }.sumOf { it.seatRank.price }
        uiModel = uiModel.copy(totalPrice = newPrice)
        view.showTotalPrice(uiModel.totalPrice)
    }

    override fun showConfirmDialog() {
        view.showReservationDialog()
    }

    override fun reserve() {
        val handler = Handler(Looper.getMainLooper())

        uiModel.screen?.let { screen ->
            uiModel.dateTime?.let { dateTime ->
                thread {
                    reservationRepository.saveReservation(
                        screen.movie.id,
                        uiModel.id,
                        screen.movie.title,
                        uiModel.ticketCount,
                        uiModel.userSeat.seatModels.filter { seatModel -> seatModel.isSelected }
                            .map { seatModel -> seatModel.toSeat() },
                        dateTime,
                    ).onSuccess { id ->
                        handler.post {
                            view.showToastMessage(ReservationSuccessMessage)
                            view.navigateToReservation(id)
                        }
                    }.onFailure { e ->
                        handler.post {
                            view.showSnackBar(e)
                            view.navigateBackToPrevious()
                        }
                    }
                }
            }
        }
    }
}
