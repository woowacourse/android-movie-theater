package woowacourse.movie.ticket.model

object MovieTicketRepositoryImpl : MovieTicketRepository {
    private val tickets = mutableMapOf<Int, MovieTicket>()
    private const val ERROR_EMPTY_TICKET = "예매 정보가 없습니다."

    override fun createMovieTicket(
        theaterName: String,
        movieTitle: String,
        screeningDate: String,
        reservationCount: Int,
        reservationSeats: String,
        totalPrice: Int,
    ): MovieTicket {
        val id = IdGenerator.generateId()
        val newTicket = MovieTicket(
            id,
            theaterName,
            movieTitle,
            screeningDate,
            reservationCount,
            reservationSeats,
            totalPrice
        )
        tickets[id] = newTicket
        return newTicket
    }

    override fun getMovieTicket(movieTicketId: Int): MovieTicket =
        tickets[movieTicketId] ?: throw IllegalStateException(ERROR_EMPTY_TICKET)
}
