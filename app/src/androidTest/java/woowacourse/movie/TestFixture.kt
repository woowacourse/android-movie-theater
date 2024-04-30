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
    val theaters: List<Theater> = TheaterDao().findAll()

    fun makeMockTicket(): Ticket {
        val movie = movies[FIRST_MOVIE_ITEM_POSITION]
        val theater = theaters[FIRST_THEATER_ITEM_POSITION]
        val dateTime = ScreeningDateTime(movie.screeningPeriod[0].toString(), theater.screeningTimes.weekDay.toString())
        return Ticket(2, dateTime)
    }

    fun makeMockSeats(): Seats {
        val seats = Seats()
        seats.manageSelected(true, Seat('A', 2, Grade.B))
        seats.manageSelected(true, Seat('C', 3, Grade.S))
        return seats
    }
}
