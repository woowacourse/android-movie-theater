package woowacourse.movie.presentation.choiceSeat

import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.domain.model.tools.Ticket
import woowacourse.movie.domain.model.tools.seat.Location
import woowacourse.movie.domain.model.tools.seat.SeatGrade

interface ChoiceSeatContract {
    interface View {
        val presenter: Presenter

        fun updateTextChoicePaymentAmount(paymentAmount: Int)

        fun updateConfirmButtonState(reservationCountFull: Boolean)
    }

    interface AlarmManager {
        fun setAlarm(ticket: Ticket)
    }

    interface Presenter {
        val view: View
        val alarmManager: AlarmManager

        fun issueTicket(): Ticket

        fun checkReservationCountFull(): Boolean
        fun addSeat(row: Int, column: Int)

        fun removeSeat(row: Int, column: Int)

        fun getLocation(row: Int, column: Int): Location
        fun getSeatGrade(location: Location): SeatGrade

        fun getMovieById(movieId: Long): Movie
    }
}
