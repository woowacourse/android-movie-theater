package woowacourse.movie.presentation.booking

import woowacourse.movie.domain.model.tools.Movie
import java.time.LocalDate
import java.time.LocalTime

interface BookingContract {
    interface View {
        val presenter: Presenter

        fun setTicketCount(ticketCount: Int)

        fun setDateSpinnerItems(items: List<LocalDate>)

        fun setTimeSpinnerItems(items: List<LocalTime>)
    }

    interface Presenter {
        val view: View

        fun plusTicketCount()

        fun minusTicketCount()

        fun getTicketCurrentCount(): Int

        fun getScreeningTimes(date: LocalDate): List<LocalTime>

        fun getScreeningDates(movieId: Long): List<LocalDate>

        fun getMovieById(movieId: Long): Movie
    }
}
