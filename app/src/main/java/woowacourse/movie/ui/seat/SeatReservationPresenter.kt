package woowacourse.movie.ui.seat

import android.view.View
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
    private val loadedAllSeats: Seats = screenDataSource.seats(timeReservation.screenData.id)
    private val ticketCount = timeReservation.ticket.count

    private var selectedSeats = Seats()

    override fun loadAllSeats() {
        view.showAllSeats(loadedAllSeats)
    }

    override fun loadTimeReservation() {
        view.showTimeReservation(timeReservation)
        view.showTotalPrice(selectedSeats.totalPrice())
    }

    override fun selectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)

        if (selectedSeats.seats.size >= ticketCount) {
            view.showSelectedSeatFail(IllegalArgumentException("exceed ticket count that can be reserved."))
            return
        }
        selectedSeats = selectedSeats.add(seat)
        view.showSelectedSeat(seatView)
    }

    override fun deselectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)
        if (selectedSeats.seats.contains(seat)) {
            selectedSeats = selectedSeats.remove(seat)
        }
        view.showDeselectedSeat(seatView)
    }

    override fun calculateTotalPrice() {
        view.showTotalPrice(selectedSeats.totalPrice())

        val reservationActivated = selectedSeats.count() == ticketCount
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
                selectedSeats,
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
        private const val TAG = "SeatReservationPresente"
    }
}
