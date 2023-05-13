package woowacourse.movie.ticket

import domain.BookingMovie
import woowacourse.movie.dto.movie.BookingMovieDto
import woowacourse.movie.mapper.movie.mapToDomain

class TicketPresenter(private val view: TicketContract.View) : TicketContract.Presenter {
    override lateinit var bookingMovie: BookingMovie

    override fun initActivity(data: BookingMovieDto) {
        bookingMovie = data.mapToDomain()
        view.showMovieTitle(bookingMovie.title)
        view.showMovieDate(getDateInfo(bookingMovie))
        view.showTicketInfo(getTicketInfo(bookingMovie))
        view.showTicketPrice(view.formatTicketPrice(bookingMovie.price))
    }

    override fun getDateInfo(bookingMovie: BookingMovie): String {
        val date = bookingMovie.date.date
        val time = bookingMovie.time.time
        return view.formatTicketDateTime(date, time)
    }

    override fun getTicketInfo(bookingMovie: BookingMovie): String {
        val count = bookingMovie.ticketCount.numberOfPeople
        val seats = bookingMovie.seats
        val theater = bookingMovie.theaterName
        return view.formatTicketSeat(count, seats, theater)
    }
}
