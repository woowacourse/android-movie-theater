package woowacourse.movie.movie.ticket

import woowacourse.movie.movie.dto.movie.BookingMovieEntity

class TicketPresenter(private val view: TicketContract.View) : TicketContract.Presenter {
    override fun initActivity(bookingMovieEntity: BookingMovieEntity) {
        view.showMovieInfo(bookingMovieEntity.title, getDateInfo(bookingMovieEntity))
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

    // override fun getTicketPrice(seats: SeatsDto, date: LocalDate, time: LocalTime): String {
    //     val totalTicketPrice = seats.mapToSeats().caculateSeatPrice(LocalDateTime.of(date, time))
    //     return view.formatTicketPrice(totalTicketPrice)
    // }
}
