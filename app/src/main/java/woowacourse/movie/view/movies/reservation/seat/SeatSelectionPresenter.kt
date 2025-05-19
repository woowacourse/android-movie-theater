package woowacourse.movie.view.movies.reservation.seat

import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.SeatFactory
import woowacourse.movie.domain.model.SettingData
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.repository.SettingRepository
import woowacourse.movie.repository.TicketRepository

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val repository: TicketRepository,
    private val settingRepository: SettingRepository,
) : SeatSelectionContract.Presenter {
    private lateinit var reservationInfo: ReservationInfo
    private lateinit var ticket: Ticket

    private val seatFactory = SeatFactory.default()
    private val seats = seatFactory.seats

    override fun loadSeats(reservationInfo: ReservationInfo) {
        this.reservationInfo = reservationInfo
        ticket = reservationInfo.toTicket(listOf())
        view.showSeats(seats, listOf())
        updateScreen()
    }

    override fun loadSeats(
        reservationInfo: ReservationInfo,
        ticket: Ticket,
    ) {
        this.ticket = ticket
        view.showSeats(seats, ticket.seats)
        updateScreen()
    }

    override fun selectSeat(seat: Seat) {
        runCatching {
            ticket = ticket.updateSeats(seat)
            view.showSeats(seats, ticket.seats)
            updateScreen()
        }.onFailure { e ->
            view.showError(e.message)
        }
    }

    private fun updateScreen() {
        view.updateTicketInfo(ticket)
    }

    override fun showConfirmButton() {
        view.showReservationDialog()
    }

    override fun completeReservation() {
        repository.save(ticket) {
            it.onFailure { e ->
                view.showError(e.message)
            }
        }
        settingRepository.findAll {
            it.onSuccess {
                val isEnabled = it.find { it.key == SettingData.NOTIFICATION_KEY }?.value ?: false
                view.setAlarm(isEnabled, ticket, ticket.showTime.minusMinutes(30))
            }
        }

        view.navigateToResult(ticket)
    }
}
