package woowacourse.movie.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import junit.framework.TestCase.assertEquals
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import woowacourse.movie.data.model.MovieData
import woowacourse.movie.data.model.ReservationTicket
import woowacourse.movie.data.model.ScreenData
import woowacourse.movie.data.source.ReservationTicketDao
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Grade
import woowacourse.movie.domain.model.Position
import woowacourse.movie.domain.model.Seat
import woowacourse.movie.domain.model.Seats
import woowacourse.movie.domain.model.Theater
import java.time.LocalDate
import java.time.LocalTime

@RunWith(AndroidJUnit4::class)
class ReservationTicketDatabaseTest {
    private lateinit var dao: ReservationTicketDao
    private lateinit var db: ReservationTicketDatabase

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, ReservationTicketDatabase::class.java).build()
        dao = db.reservationDao()
    }

    @After
    fun tearDown() {
        db.close()
    }

    @Test
    fun testInsert() {
        val movieData = MovieData(1, "title", 120, "description")
        val screenData = ScreenData(1, movieData, DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)))
        val seats =
            Seats(
                Seat(Position(1, 1), Grade.A),
            )
        val reservationTicket =
            ReservationTicket(
                id = 1,
                screenData = screenData,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats,
                theater = Theater(id = 1, name = "theater1", screens = emptyList()),
            )

        dao.insert(reservationTicket)

        assertEquals(reservationTicket, dao.findReservationById(1))
    }

    @Test
    fun testInsertAndRetrieve() {
        val movieData = MovieData(1, "title", 120, "description")
        val screenData = ScreenData(1, movieData, DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)))
        val seats =
            Seats(
                Seat(Position(1, 1), Grade.A),
            )
        val reservationTicket =
            ReservationTicket(
                id = 1,
                screenData = screenData,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats,
                theater = Theater(id = 1, name = "theater1", screens = emptyList()),
            )

        val insertedReservationTicketId = dao.insert(reservationTicket)

        assertEquals(1, insertedReservationTicketId)
    }

    @Test
    fun testFindAllReservationTickets() {
        val movieData1 = MovieData(1, "title1", 120, "description1")
        val movieData2 = MovieData(2, "title2", 120, "description2")
        val movieData3 = MovieData(3, "title3", 120, "description3")

        val screenData1 = ScreenData(1, movieData1, DateRange(LocalDate.of(2021, 1, 1), LocalDate.of(2021, 12, 31)))
        val screenData2 = ScreenData(2, movieData2, DateRange(LocalDate.of(2022, 2, 1), LocalDate.of(2022, 3, 10)))
        val screenData3 = ScreenData(3, movieData3, DateRange(LocalDate.of(2023, 2, 10), LocalDate.of(2023, 10, 7)))

        val seats1 =
            Seats(
                Seat(Position(1, 1), Grade.A),
                Seat(Position(3, 2), Grade.B),
                Seat(Position(1, 3), Grade.S),
            )

        val seats2 =
            Seats(
                Seat(Position(2, 1), Grade.A),
                Seat(Position(1, 2), Grade.B),
                Seat(Position(3, 3), Grade.S),
            )

        val seats3 =
            Seats(
                Seat(Position(3, 1), Grade.A),
                Seat(Position(2, 2), Grade.B),
                Seat(Position(1, 3), Grade.S),
            )

        val reservationTicket1 =
            ReservationTicket(
                id = 1,
                screenData = screenData1,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats1,
                theater = Theater(id = 1, name = "theater1", screens = emptyList()),
            )
        val reservationTicket2 =
            ReservationTicket(
                id = 2,
                screenData = screenData2,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats2,
                theater = Theater(id = 1, name = "theater1", screens = emptyList()),
            )
        val reservationTicket3 =
            ReservationTicket(
                id = 3,
                screenData = screenData3,
                date = LocalDate.of(2024, 3, 2),
                time = LocalTime.of(12, 30),
                seats = seats3,
                theater = Theater(id = 1, name = "theater1", screens = emptyList()),
            )

        dao.insert(reservationTicket1)
        dao.insert(reservationTicket2)
        dao.insert(reservationTicket3)

        val foundAllReservationTickets = dao.findAll()
        val expected = listOf(reservationTicket1, reservationTicket2, reservationTicket3)

        assertEquals(expected, foundAllReservationTickets)
    }
}
