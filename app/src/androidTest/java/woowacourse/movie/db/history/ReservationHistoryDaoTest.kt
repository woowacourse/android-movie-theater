package woowacourse.movie.db.history

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.runner.RunWith
import woowacourse.movie.TestFixture.makeMockTicket

@RunWith(AndroidJUnit4::class)
class ReservationHistoryDaoTest {
    private lateinit var reservationDao: ReservationHistoryDao
    private lateinit var db: ReservationHistoryDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        db = Room.inMemoryDatabaseBuilder(context, ReservationHistoryDatabase::class.java).build()
        reservationDao = db.reservationDao()
    }

    @Test
    fun `티켓_저장과_저장된_데이터의_로드를_확인한다`() {
        // given
        val reservationTicket = makeMockTicket().toReservationTicket("영화 제목", "극장 이름")
        reservationDao.saveReservationTicket(reservationTicket)
        val reservations = reservationDao.findReservations()

        // then
        reservations.first().apply {
            assertEquals(movieId, reservationTicket.movieId)
            assertEquals(movieTitle, reservationTicket.movieTitle)
            assertEquals(amount, reservationTicket.amount)
            assertEquals(theaterId, reservationTicket.theaterId)
            assertEquals(screeningDateTime.time, reservationTicket.screeningDateTime.time)
            assertEquals(screeningDateTime.date, reservationTicket.screeningDateTime.date)
            assertEquals(seatSelection.theaterSeats.size, reservationTicket.seatSelection.theaterSeats.size)
        }
        assertEquals(1, reservations.size)
    }

    @Test
    fun `티켓의_고유값으로_저장_된_티켓을_찾아오는지_확인한다`() {
        // given
        val reservationTicket = makeMockTicket().toReservationTicket("영화 제목", "극장 이름")
        reservationDao.saveReservationTicket(reservationTicket)
        val actualReservation = reservationDao.findReservations().first()
        val expectedReservation = reservationDao.findReservationById(actualReservation.ticketId)

        // then
        actualReservation.apply {
            assertEquals(movieId, expectedReservation?.movieId)
            assertEquals(movieTitle, expectedReservation?.movieTitle)
            assertEquals(amount, expectedReservation?.amount)
            assertEquals(theaterId, expectedReservation?.theaterId)
            assertEquals(screeningDateTime.time, expectedReservation?.screeningDateTime?.time)
            assertEquals(screeningDateTime.date, expectedReservation?.screeningDateTime?.date)
            assertEquals(seatSelection.theaterSeats.size, expectedReservation?.seatSelection?.theaterSeats?.size)
        }
    }
}
