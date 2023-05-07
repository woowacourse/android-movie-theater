package woowacourse.movie.movie.ticket

import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.dto.seat.SeatsDto
import woowacourse.movie.movie.mapper.seat.mapToSeats
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class TicketPresenter(private val view: TicketContract.View) : TicketContract.Presenter {
    override fun initActivity(bookingMovieEntity: BookingMovieEntity) {
        view.showMovieInfo(bookingMovieEntity.movie.title, getDateInfo(bookingMovieEntity))
        view.showTicketInfo(getTicketInfo(bookingMovieEntity))
        view.showTicketPrice(getTicketPrice(bookingMovieEntity.seats, bookingMovieEntity.date.date, bookingMovieEntity.time.time))
    }

    override fun getDateInfo(bookingMovieEntity: BookingMovieEntity): String {
        val date = bookingMovieEntity.date.date
        val time = bookingMovieEntity.time.time
        return view.formatTicketDateTime(date, time)
    }

    override fun getTicketInfo(bookingMovieEntity: BookingMovieEntity): String {
        val count = bookingMovieEntity.ticketCount.numberOfPeople
        val seats = bookingMovieEntity.seats.getSeatsPositionToString()
        val theater = bookingMovieEntity.theaterName
        return view.formatTicketSeat(count, seats, theater)
    }

    override fun getTicketPrice(seats: SeatsDto, date: LocalDate, time: LocalTime): String {
        val totalTicketPrice = seats.mapToSeats().caculateSeatPrice(LocalDateTime.of(date, time))
        return view.formatTicketPrice(totalTicketPrice)
    }
}
