package woowacourse.movie.ticket.view.contract

import domain.BookingMovie
import woowacourse.movie.ticket.model.BookingMovieModel
import java.time.LocalDate
import java.time.LocalTime

interface TicketContract {
    interface View {
        val presenter: Presenter
        fun showTicketInfo(bookingMovieModel: BookingMovieModel)
        fun formatTicketDateTime(date: LocalDate, time: LocalTime): String
        fun formatTicketSeat(count: Int, seats: String, theater: String): String
        fun formatTicketPrice(totalTicketPrice: Int): String
    }

    interface Presenter {
        var bookingMovie: BookingMovie
        fun getData(data: BookingMovieModel)
        fun updateTicketInfo()
    }
}
