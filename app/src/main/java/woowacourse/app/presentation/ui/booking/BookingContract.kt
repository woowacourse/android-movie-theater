package woowacourse.app.presentation.ui.booking

import woowacourse.domain.movie.Movie
import java.time.LocalDate
import java.time.LocalTime

interface BookingContract {
    interface View {
        val presenter: Presenter
        fun initDateTimes(dates: List<LocalDate>)
        fun showTicketCount(value: Int)
        fun startSeatActivity(ticketCount: Int, movie: Movie)
        fun initScreeningTimes(fetchTimes: (screeningDate: LocalDate) -> List<LocalTime>)
        fun showMovie(movie: Movie)
    }

    interface Presenter {
        fun getTicketCount(): Int
        fun restoreTicketCount(value: Int)
        fun initView()

        fun subTicket()
        fun addTicket()
        fun fetchScreeningTimes()
        fun completeBooking()
    }
}
