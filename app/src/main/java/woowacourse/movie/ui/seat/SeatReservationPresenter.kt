package woowacourse.movie.ui.seat

import android.view.View
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Screen
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository

class SeatReservationPresenter(
    private val view: SeatReservationContract.View,
    private val screenRepository: ScreenRepository,
    private val reservationRepository: ReservationRepository,
) : SeatReservationContract.Presenter {
    private var selectedSeats = Seats()
    private var ticketCount = 0
    private var loadedAllSeats: Seats = Seats()

    override fun loadData(timeReservationId: Int) {
        val timeReservation = reservationRepository.loadTimeReservation(timeReservationId)
        val seats = screenRepository.seats(timeReservation.screen.id)
        this.loadedAllSeats = seats
        ticketCount = timeReservation.ticket.count

        view.showAllSeats(seats)
        view.initBinding(selectedSeats.totalPrice(), timeReservation)
        view.updateTotalPrice(selectedSeats.totalPrice())
    }

    override fun selectSeat(
        position: Position,
        seatView: View,
    ) {
        val seat = loadedAllSeats.findSeat(position)
        if (selectedSeats.seats.contains(seat)) {
            seatView.isSelected = !seatView.isSelected
            selectedSeats = selectedSeats.remove(seat)
        } else {
            if (selectedSeats.seats.size >= ticketCount) {
                view.showToast(IllegalArgumentException("exceed ticket count that can be reserved."))
            } else {
                seatView.isSelected = !seatView.isSelected
                selectedSeats = selectedSeats.add(seat)
            }
        }

        view.updateTotalPrice(selectedSeats.totalPrice())
        if (selectedSeats.count() == ticketCount) {
            view.activateReservation(true)
        } else {
            view.activateReservation(false)
        }
    }

    private fun screen(id: Int): Screen {
        screenRepository.findById(id = id).onSuccess { screen ->
            return screen
        }.onFailure { e ->
            throw e
        }
        throw IllegalStateException("예기치 못한 오류")
    }

    override fun attemptReservation(
        screenId: Int,
        theaterId: Int,
    ) {
        view.showDialog(screenId, theaterId)
    }

    override fun completeReservation(
        screenId: Int,
        theaterId: Int,
    ) {
        val timeReservation = reservationRepository.loadTimeReservation(screenId)

        reservationRepository.save(
            screen(screenId),
            selectedSeats,
            timeReservation.dateTime,
        ).onSuccess { id ->
            view.navigateToCompleteReservation(id, theaterId)
        }.onFailure { e ->
            view.showSeatReservationFail(e)
        }
    }
}
