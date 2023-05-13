package woowacourse.movie.ticket

import domain.BookingMovie
import woowacourse.movie.dto.movie.BookingMovieDto
import java.time.LocalDate
import java.time.LocalTime

interface TicketContract {
    interface View {
        val presenter: Presenter
        fun showMovieTitle(title: String)
        fun showMovieDate(date: String)
        fun showTicketInfo(seatInfo: String)
        fun showTicketPrice(ticketPrice: String)
        fun formatTicketDateTime(date: LocalDate, time: LocalTime): String
        fun formatTicketSeat(count: Int, seats: String, theater: String): String
        fun formatTicketPrice(totalTicketPrice: Int): String
    }

    interface Presenter {
        var bookingMovie: BookingMovie
        fun initActivity(data: BookingMovieDto)
        fun getDateInfo(bookingMovie: BookingMovie): String
        fun getTicketInfo(bookingMovie: BookingMovie): String
    }
}
