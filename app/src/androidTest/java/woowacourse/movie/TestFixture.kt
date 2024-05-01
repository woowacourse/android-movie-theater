package woowacourse.movie

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.Theater
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket

object TestFixture {
    const val FIRST_MOVIE_ITEM_POSITION = 0
    val movies: List<Movie> = ScreeningDao().findAll()
    const val FIRST_THEATER_ITEM_POSITION = 0
    val theaterDao = TheaterDao()

    fun makeMockTicket(): Ticket {
        val movie = movies[FIRST_MOVIE_ITEM_POSITION]
        val theater: Theater = theaterDao.findTheaterByMovieId(movie.id)[FIRST_THEATER_ITEM_POSITION]
        val seats = makeMockSeats()
        val amount = seats.calculateAmount()
        return Ticket(movie.id, theater.theaterId, seats, ScreeningDateTime("", ""), amount)
    }

    fun makeMockSeats(): Seats {
        val seats = Seats()
        seats.manageSelected(true, Seat('A', 2, Grade.B))
        seats.manageSelected(true, Seat('C', 3, Grade.S))
        return seats
    }
}
