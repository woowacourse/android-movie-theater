package woowacourse.app.presentation.ui.booking

import woowacourse.domain.movie.Movie
import java.time.LocalDate
import java.time.LocalTime

interface BookingContract {
    interface View {
        val presenter: Presenter
        fun initSpinnerDateTime(dates: List<LocalDate>, times: List<LocalTime>)
        fun setTicketCountText(value: Int)
        fun startSeatActivity(ticketCount: Int)
    }

    interface Presenter {
        val movie: Movie

        fun getTicketCount(): Int
        fun restoreTicketCount(value: Int)
        fun initTicketCount()
        fun subTicket()
        fun addTicket()

        fun initDateTimes()
        fun getScreeningTimes(date: LocalDate): List<LocalTime>
        fun completeBooking()
    }
}
