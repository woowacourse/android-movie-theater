package woowacourse.app.presentation.ui.booking

import woowacourse.domain.movie.Movie
import java.time.LocalDate
import java.time.LocalTime

interface BookingContract {
    interface View {
        val presenter: Presenter
        fun initDateTimes(dates: List<LocalDate>)
        fun showTicketCount(value: Int)
        fun startSeatActivity(ticketCount: Int)
        fun initScreeningTimes(fetchTimes: (screeningDate: LocalDate) -> List<LocalTime>)
    }

    interface Presenter {
        val movie: Movie

        fun getTicketCount(): Int
        fun restoreTicketCount(value: Int)
        fun initTicketCount()
        fun subTicket()
        fun addTicket()

        fun initDateTimes()
        fun fetchScreeningTimes()
        fun completeBooking()
    }
}
