package woowacourse.movie

import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.ticket.Ticket

object TestFixture {
    const val FIRST_MOVIE_ITEM_POSITION = 0
    val movies: List<Movie> = ScreeningDao().findAll()

    fun makeMockTicket(): Ticket {
        val movie = movies[FIRST_MOVIE_ITEM_POSITION]
        // TODO : movie가 아닌 극장에서 시간정보 얻어서 넣기
        val dateTime = ScreeningDateTime(movie.screeningPeriod[0].toString(), "10:00")
        return Ticket(2, dateTime)
    }

    fun makeMockSeats(): Seats {
        val seats = Seats()
        seats.manageSelected(true, Seat('A', 2, Grade.B))
        seats.manageSelected(true, Seat('C', 3, Grade.S))
        return seats
    }
}
