package woowacourse.movie.ui.seat

import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.repository.ReservationRepository
import woowacourse.movie.data.source.DummyTheatersDataSource
import woowacourse.movie.data.source.ScreenDataSource
import woowacourse.movie.data.source.TheaterDataSource
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import kotlin.concurrent.thread

class SeatReservationPresenter(
    private val view: SeatReservationContract.View,
    private val screenDataSource: ScreenDataSource,
    private val reservationRepository: ReservationRepository,
    private val theaterDataSource: TheaterDataSource = DummyTheatersDataSource(),
    private val theaterId: Int,
    timeReservationId: Int,
) : SeatReservationContract.Presenter {
    private val timeReservation: TimeReservation = reservationRepository.loadTimeReservation(timeReservationId)
    private var allSeats: Seats = screenDataSource.seats(timeReservation.screenData.id)
    private val ticketCount = timeReservation.ticket.count

    override fun loadAllSeats() {
        view.showAllSeats(allSeats)
    }

    override fun loadTimeReservation() {
        view.showTimeReservation(timeReservation)

        val totalPrice = allSeats.selectedSeats().totalPrice()
        view.showTotalPrice(totalPrice)
    }

    override fun selectSeat(
        position: Position,
        selection: Boolean,
    ) {
        if (selection && isSelectedFullCount()) {
            return
        }
        updateSeats(position, selection)
    }

    private fun isSelectedFullCount(): Boolean {
        if (allSeats.countSelected() >= ticketCount) {
            view.showSelectedSeatFail(IllegalArgumentException("exceed ticket count that can be reserved."))
            return true
        }
        return false
    }

    private fun updateSeats(
        position: Position,
        selection: Boolean,
    ) {
        allSeats = allSeats.updatedSeats(position, selection)
        view.showSeats(allSeats)
    }

    override fun calculateTotalPrice() {
        val totalPrice = allSeats.selectedSeats().totalPrice()
        view.showTotalPrice(totalPrice)

        val reservationActivated = allSeats.countSelected() == ticketCount
        view.activateReservation(reservationActivated)
    }

    override fun attemptReserve() {
        view.checkReservationConfirm()
    }

    override fun reserve() {
        val screenId = timeReservation.screenData.id
        thread {
            reservationRepository.savedReservationId(
                loadedScreen(screenId),
                allSeats.selectedSeats(),
                timeReservation.dateTime,
                theaterDataSource.findById(theaterId),
            ).onSuccess { reservationTicketId ->
                view.showCompleteReservation(reservationTicketId.toInt())
            }.onFailure { e ->
                view.showSeatReservationFail(e)
            }
        }
    }

    private fun loadedScreen(screenId: Int): ScreenData {
        screenDataSource.findById(id = screenId).onSuccess { screen ->
            return screen
        }.onFailure { e ->
            throw e
        }
        throw IllegalStateException("예기치 못한 오류")
    }

    companion object {
        private const val TAG = "SeatReservationPresenter"
    }
}
