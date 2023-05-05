package woowacourse.movie.ui.booking

import woowacourse.movie.model.BookedMovie
import woowacourse.movie.model.main.MovieUiModel
import woowacourse.movie.movie.Movie
import woowacourse.movie.theater.Theater
import woowacourse.movie.ticket.TicketCount
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

interface BookingContract {

    interface View {

        fun initView(movie: MovieUiModel)
        fun setTicketCountText(count: Int)
        fun setTimes(screeningTimes: List<LocalTime>)
        fun setDates(screeningDates: List<LocalDate>)
    }

    interface Presenter {
        val movie: Movie
        var ticketCount: TicketCount
        val theater: Theater

        fun initMovie()
        fun initTicketCount()
        fun initDateTimes()
        fun minusTicketCount()
        fun plusTicketCount()
        fun createBookedMovie(dateTime: LocalDateTime): BookedMovie
    }
}
