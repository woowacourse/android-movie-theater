package woowacourse.movie.movie.ticket

import woowacourse.movie.movie.dto.movie.BookingMovieEntity

class TicketPresenter(private val view: TicketContract.View) : TicketContract.Presenter {
    override lateinit var bookingMovieEntity: BookingMovieEntity

    override fun initActivity(data: BookingMovieEntity) {
        bookingMovieEntity = data
        view.showMovieTitle(bookingMovieEntity.title)
        view.showMovieDate(getDateInfo(bookingMovieEntity))
        view.showTicketInfo(getTicketInfo(bookingMovieEntity))
        view.showTicketPrice(view.formatTicketPrice(bookingMovieEntity.price))
    }

    override fun getDateInfo(bookingMovieEntity: BookingMovieEntity): String {
        val date = bookingMovieEntity.date.date
        val time = bookingMovieEntity.time.time
        return view.formatTicketDateTime(date, time)
    }

    override fun getTicketInfo(bookingMovieEntity: BookingMovieEntity): String {
        val count = bookingMovieEntity.ticketCount.numberOfPeople
        val seats = bookingMovieEntity.seats
        val theater = bookingMovieEntity.theaterName
        return view.formatTicketSeat(count, seats, theater)
    }
}
