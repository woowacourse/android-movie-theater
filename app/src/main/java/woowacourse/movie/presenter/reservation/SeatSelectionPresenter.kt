package woowacourse.movie.presenter.reservation

import woowacourse.movie.contract.reservation.SeatSelectionContract
import woowacourse.movie.data.reservation.ReservationData
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Reservation
import java.time.LocalDateTime
import kotlin.concurrent.thread

class SeatSelectionPresenter(
    private val view: SeatSelectionContract.View,
    private val title: String,
    private val count: Int,
    private val showtime: LocalDateTime,
    private val cinemaName: String,
    private val reservationData: ReservationData,
    private val runOnUiThread: (Runnable) -> Unit,
    selectedSeats: Set<Seat>?,
) : SeatSelectionContract.Presenter {
    private val seats: Set<Seat> = Seat.seats()
    private var selectedSeats = selectedSeats?.toSet() ?: emptySet()
    private val completable get() = count == selectedSeats.size
    private val price: Int get() = selectedSeats.sumOf(Seat::price)

    override fun presentSeats() {
        view.setSeats(seats, selectedSeats)
    }

    override fun presentTitle() {
        view.setTitle(title)
    }

    override fun presentPrice() {
        view.setPrice(price)
    }

    override fun presentCompleteButton() {
        view.setConfirmEnabled(completable)
    }

    override fun onSeatSelect(seat: Seat) {
        if (seat.selected) {
            selectedSeats -= seat
        } else {
            if (canSelectMoreSeat) {
                selectedSeats += seat
            }
        }

        view.setSeatIsSelected(seat, seat.selected)
        view.setPrice(price)
        view.setConfirmEnabled(completable)
    }

    private val Seat.selected: Boolean get() = this in selectedSeats

    private val canSelectMoreSeat: Boolean get() = selectedSeats.size < count

    override fun tryReservation() {
        view.askFinalReservation()
    }

    override fun confirmReservation() {
        val reservation = Reservation(title, showtime, selectedSeats, cinemaName)
        thread {
            reservationData.add(reservation)
            runOnUiThread {
                view.navigateToTicketScreen(reservation)
            }
        }
    }
}
