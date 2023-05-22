package woowacourse.movie.ui.selectseat

import android.widget.Button
import androidx.appcompat.app.AlertDialog
import domain.Reservation
import domain.Tickets
import woowacourse.movie.data.model.SeatView
import woowacourse.movie.data.model.uimodel.TheaterUIModel
import java.time.LocalDateTime

interface SelectSeatContract {
    interface View {
        var presenter: Presenter

        fun createReservationAlertDialog(): AlertDialog

        fun updateSeatView(seatView: SeatView, isSelected: Boolean)

        fun updateTicketView()
    }

    interface Presenter {
        val tickets: Tickets

        fun setSeatTable()

        fun updateSeatState(seatView: SeatView, dateTime: LocalDateTime, theater: TheaterUIModel)

        fun updateButtonState(button: Button)
        fun updateReservation(reservation: Reservation)
    }
}
