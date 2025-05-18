package woowacourse.movie.ui.view.seat

import woowacourse.movie.domain.reservation.PurchaseType
import woowacourse.movie.domain.reservation.Seat
import woowacourse.movie.domain.ticket.Ticket
import java.time.LocalDateTime

interface SeatSelectionContract {
    interface Presenter {
        fun presentSeats()

        fun presentTitle()

        fun presentPrice()

        fun presentCompleteButton()

        fun onSeatSelect(seat: Seat)

        fun tryReservation()

        fun confirmReservation()

        fun getSelectedSeats(): Set<Seat>
    }

    interface View {
        fun setSeats(
            seats: Set<Seat>,
            selectedSeats: Set<Seat>,
        )

        fun setTitle(title: String)

        fun setPrice(price: Int)

        fun setSeatIsSelected(
            seat: Seat,
            selected: Boolean,
        )

        fun setConfirmEnabled(isEnabled: Boolean)

        fun askFinalReservation()

        fun setTicketAlarm(
            ticket: Ticket,
            isTicketAlarmChecked: Boolean,
        )

        fun navigateToTicketScreen(
            title: String,
            count: Int,
            showtime: LocalDateTime,
            cinemaName: String,
            seats: Set<Seat>,
            purchaseType: PurchaseType,
        )
    }
}
