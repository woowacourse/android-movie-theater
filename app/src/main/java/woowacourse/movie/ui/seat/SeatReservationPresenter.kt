package woowacourse.movie.ui.seat

import android.view.View
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.TimeReservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository

class SeatReservationPresenter(
    private val view: SeatReservationContract.View,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
) : SeatReservationContract.Presenter {
    private var theaterId: Int = 0
    private var timeReservationId: Int = 0

    private lateinit var timeReservation: TimeReservation
    private var loadedAllSeats: Seats = Seats()
    private var selectedSeats = Seats()
    private var ticketCount = 0

    override fun saveId(
        theaterId: Int,
        timeReservationId: Int,
    ) {
        this.theaterId = theaterId
        this.timeReservationId = timeReservationId

        timeReservation = reservationRepository.loadTimeReservation(timeReservationId)
        loadedAllSeats = screenRepository.seats(timeReservation.screen.id)
        ticketCount = timeReservation.ticket.count
    }

    override fun loadAllSeats() {
        view.showAllSeats(loadedAllSeats)
    }

    override fun loadTimeReservation() {
        view.showTimeReservation(timeReservation)
    }

    override fun selectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)

        if (toggleSeatSelection(seat, seatView)) {
            view.activateReservation(selectedSeats.count() == ticketCount)
            view.updateTotalPrice(selectedSeats.totalPrice())
        }
    }

    private fun toggleSeatSelection(
        seat: Seat,
        seatView: View,
    ): Boolean =
        when {
            selectedSeats.seats.contains(seat) -> {
                deselectSeat(seatView, seat)
                true
            }

            selectedSeats.seats.size < ticketCount -> {
                selectSeat(seatView, seat)
                true
            }

            else -> {
                view.showToast(IllegalArgumentException("exceed ticket count that can be reserved."))
                false
            }
        }

    private fun selectSeat(
        seatView: View,
        seat: Seat,
    ) {
        seatView.isSelected = true
        selectedSeats = selectedSeats.add(seat)
    }

    private fun deselectSeat(
        seatView: View,
        seat: Seat,
    ) {
        seatView.isSelected = false
        selectedSeats = selectedSeats.remove(seat)
    }

    override fun reserve(theaterId: Int) {
        val screenId = timeReservation.screen.id

        reservationRepository.save(
            loadedScreen(screenId),
            selectedSeats,
            timeReservation.dateTime,
        ).onSuccess { reservationId ->
            view.navigateToCompleteReservation(reservationId, theaterId)
        }.onFailure { e ->
            view.showSeatReservationFail(e)
        }
    }

    private fun loadedScreen(screenId: Int): Screen {
        screenRepository.findById(id = screenId).onSuccess { screen ->
            return screen
        }.onFailure { e ->
            throw e
        }
        throw IllegalStateException("예기치 못한 오류")
    }
}