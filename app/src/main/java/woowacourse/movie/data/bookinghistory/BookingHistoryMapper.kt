package woowacourse.movie.data.bookinghistory

import woowacourse.movie.domain.model.movie.MovieTicket

object BookingHistoryMapper {
    fun mapFromBookingHistory(bookingHistory: BookingHistory): MovieTicket {
        return MovieTicket(
            movieTitle = bookingHistory.title,
            theaterName = bookingHistory.theater,
            screeningDateTime = bookingHistory.screeningDateTime,
            headCount = bookingHistory.headCount,
            amount = bookingHistory.totalAmount,
            seats = bookingHistory.selectedSeats,
        )
    }

    fun mapToBookingHistory(movieTicket: MovieTicket): BookingHistory {
        return BookingHistory(
            title = movieTicket.movieTitle,
            theater = movieTicket.theaterName,
            screeningDateTime = movieTicket.screeningDateTime,
            headCount = movieTicket.headCount,
            totalAmount = movieTicket.amount,
            selectedSeats = movieTicket.seats,
        )
    }
}
