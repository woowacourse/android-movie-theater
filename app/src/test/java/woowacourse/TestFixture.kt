package woowacourse

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.SeatSelection
import woowacourse.movie.model.seats.TheaterSeat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ticket.ReservationTicket

object TestFixture {
    private const val FIRST_MOVIE_ITEM_POSITION = 0
    private val movies: List<Movie> = ScreeningDao().findAll()
    private const val FIRST_THEATER_ITEM_POSITION = 0
    private val theaterDao = TheaterDao()

    fun makeMockReservationTicket(): ReservationTicket {
        val movie = movies[FIRST_MOVIE_ITEM_POSITION]
        val theater: Theater =
            theaterDao.findTheaterByMovieId(movie.id)[FIRST_THEATER_ITEM_POSITION]
        val seats = makeMockSeats()
        val amount = seats.calculateAmount()
        return ReservationTicket(
            ticketId = 0L,
            movieTitle = movie.title,
            movieId = movie.id,
            theaterId = theater.theaterId,
            seatSelection = seats,
            screeningDateTime = ScreeningDateTime("2024.3.2", "16:00"),
            amount = amount,
            theaterName = theater.theaterName,
        )
    }

    private fun makeMockSeats(): SeatSelection {
        val seatSelection = SeatSelection()
        seatSelection.manageSelected(true, TheaterSeat('A', 2, Grade.B))
        seatSelection.manageSelected(true, TheaterSeat('C', 3, Grade.S))
        return seatSelection
    }
}
