package woowacourse.movie.ticket.model

interface MovieTicketRepository {
    fun createMovieTicket(
        theaterName: String,
        movieTitle: String,
        screeningDate: String,
        reservationCount: Int,
        reservationSeats: String,
        totalPrice: Int,
    ): MovieTicket

    fun getMovieTicket(movieTicketId: Int): MovieTicket
}
