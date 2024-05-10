package woowacourse.movie.domain.repository

import woowacourse.movie.domain.model.movieticket.MovieTicket

interface MovieTicketRepository {
    fun createMovieTicket(
        theaterName: String,
        movieTitle: String,
        screeningDate: String,
        screeningTime: String,
        reservationCount: Int,
        reservationSeats: String,
        totalPrice: Int
    ): MovieTicket

    fun getMovieTicket(movieTicketId: Long): MovieTicket
}
