package woowacourse.movie

import android.content.Context
import woowacourse.movie.db.history.ReservationHistoryDatabase
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.SeatSelection
import woowacourse.movie.model.seats.TheaterSeat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ticket.Ticket
import woowacourse.movie.repository.ReservationTicketRepository
import woowacourse.movie.utils.MovieUtils.navigateToBottomMenu
import woowacourse.movie.view.MainActivity
import woowacourse.movie.view.history.ReservationHistoryFragment

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
        return Ticket(movie.id, theater.theaterId, seats, ScreeningDateTime("2024.3.2", "16:00"), amount)
    }

    fun makeTheater(): List<Theater> {
        val movieId = 0
        return theaterDao.findTheaterByMovieId(movieId)
    }

    private fun makeMockSeats(): SeatSelection {
        val seatSelection = SeatSelection()
        seatSelection.manageSelected(true, TheaterSeat('A', 2, Grade.B))
        seatSelection.manageSelected(true, TheaterSeat('C', 3, Grade.S))
        return seatSelection
    }

    fun MainActivity.fragmentChangeToReservationHistoryFragment() {
        supportFragmentManager.navigateToBottomMenu(
            R.id.fragment_container_main,
            ReservationHistoryFragment(),
        )
    }

    fun ReservationTicketRepository.clearReservations(context: Context) {
        val supportSQLiteDatabase =
            ReservationHistoryDatabase.getInstance(context).openHelper.writableDatabase
        supportSQLiteDatabase.execSQL("DELETE FROM reservationTicket")
    }

}
