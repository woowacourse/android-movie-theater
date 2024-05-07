package woowacourse

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
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
            seats = seats,
            screeningDateTime = ScreeningDateTime("", ""),
            amount = amount,
            theaterName = theater.theaterName,
        )
    }

    private fun makeMockSeats(): Seats {
        val seats = Seats()
        seats.manageSelected(true, Seat('A', 2, Grade.B))
        seats.manageSelected(true, Seat('C', 3, Grade.S))
        return seats
    }
}
