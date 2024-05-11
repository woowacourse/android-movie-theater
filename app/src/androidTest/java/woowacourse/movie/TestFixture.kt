package woowacourse.movie

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDatabase
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object TestFixture {
    const val FIRST_MOVIE_ITEM_POSITION = 0
    const val FIRST_THEATER_ITEM_POSITION = 0
    private val theaterDao = TheaterDao()
    val firstMockTicket: Ticket =
        Ticket(
            screeningDateTime = LocalDateTime.of(LocalDate.of(2024, 5, 9), LocalTime.of(10, 0)),
            movieTitle = "해리 포터와 마법사의 돌",
            theaterName = "선릉 극장",
            seats = makeMockSeats(),
            1,
        )

    fun saveMockTicket() {
        val db =
            Room.databaseBuilder(
                ApplicationProvider.getApplicationContext(),
                TicketDatabase::class.java,
                "reservation_history",
            ).build()
        val dao = db.ticketDao()
        dao.insert(firstMockTicket)
    }

    fun makeTheater(): List<Theater> {
        val movieId = 0
        return theaterDao.findTheaterByMovieId(movieId)
    }

    fun findScreeningTimesCount(): Int {
        val movieId = 0
        return theaterDao.findScreeningTimesByMovieId(FIRST_THEATER_ITEM_POSITION, movieId).size
    }

    fun makeMockSeats(): Seats {
        val seats = Seats()
        seats.manageSelected(true, Seat('A', 2, Grade.B))
        seats.manageSelected(true, Seat('C', 3, Grade.S))
        return seats
    }
}
