package woowacourse.movie.presentation.ui.seatselection

import woowacourse.movie.domain.model.toSeatModel
import woowacourse.movie.domain.repository.NotificationRepository
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.MessageType.ReservationSuccessMessage
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.UserSeat
import woowacourse.movie.presentation.model.toSeat
import java.time.LocalDateTime
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
    private val notificationRepository: NotificationRepository,
) : SeatSelectionContract.Presenter {
    private lateinit var uiModel: SeatSelectionUiModel
    val userSeat: UserSeat
        get() = UserSeat(uiModel.userSeat.seatModels.filter { it.isSelected })

    override fun loadScreen(reservationInfo: ReservationInfo) {
        screenRepository.findByScreenId(
            theaterId = reservationInfo.theaterId,
            movieId = reservationInfo.movieId,
        ).onSuccess { screen ->
            uiModel =
                SeatSelectionUiModel(
                    theaterId = reservationInfo.theaterId,
                    screen = screen,
                    dateTime = reservationInfo.dateTime,
                    ticketCount = reservationInfo.ticketCount,
                )
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
        uiModel.screen?.let { screen ->
            uiModel.dateTime?.let { dateTime ->
                thread {
                    reservationRepository.saveReservation(
                        screen.movie.id,
                        uiModel.theaterId,
                        screen.movie.title,
                        uiModel.ticketCount,
                        uiModel.userSeat.seatModels.filter { seatModel -> seatModel.isSelected }
                            .map { seatModel -> seatModel.toSeat() },
                        dateTime,
                    ).onSuccess { id ->
                        createNotification(id, screen.movie.title, dateTime)
                    }.onFailure { e ->
                        view.showToastMessage(e)
                        view.navigateBackToPrevious()
                    }
                }
            }
        }
    }

    private fun createNotification(
        reservationId: Long,
        movieTitle: String,
        screeningDateTime: LocalDateTime,
    ) {
        notificationRepository.createNotification(reservationId, movieTitle, screeningDateTime)
            .onSuccess {
                view.showToastMessage(ReservationSuccessMessage)
                view.navigateToReservation(reservationId)
            }.onFailure { e ->
                view.showToastMessage(e)
                view.navigateBackToPrevious()
            }
    }
}
