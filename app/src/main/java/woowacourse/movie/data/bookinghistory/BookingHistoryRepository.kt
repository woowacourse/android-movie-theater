package woowacourse.movie.data.bookinghistory

import woowacourse.movie.domain.model.movie.MovieTicket

interface BookingHistoryRepository {
    fun saveBooking(ticket: MovieTicket)

    fun getBookings(onLoaded: (List<MovieTicket>) -> Unit)
}
