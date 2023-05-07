package woowacourse.movie.movie.ticket

import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import java.time.LocalDate
import java.time.LocalTime

interface TicketContract {
    interface View {
        val presenter: Presenter
        fun showMovieInfo(title: String, date: String)
        fun showTicketInfo(seatInfo: String)
        fun showTicketPrice(ticketPrice: String)
        fun formatTicketDateTime(date: LocalDate, time: LocalTime): String
        fun formatTicketSeat(count: Int, seats: String, theater: String): String
        fun formatTicketPrice(totalTicketPrice: Int): String
    }

    interface Presenter {
        fun initActivity(bookingMovieEntity: BookingMovieEntity)
        fun getDateInfo(bookingMovieEntity: BookingMovieEntity): String
        fun getTicketInfo(bookingMovieEntity: BookingMovieEntity): String
        // fun getTicketPrice(seats: SeatsDto, date: LocalDate, time: LocalTime): String
    }
}
