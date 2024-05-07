package woowacourse.movie.db.history

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.runner.RunWith
import woowacourse.movie.TestFixture.makeMockTicket
import woowacourse.movie.model.ticket.toReservationTicket

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

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun `티켓_저장과_저장된_데이터의_로드를_확인한다`() {
        //given
        val reservationTicket = makeMockTicket().toReservationTicket("영화 제목", "극장 이름")
        reservationDao.saveReservationTicket(reservationTicket)
        val reservations = reservationDao.findReservations()

        //then
        reservations.first().apply {
            assertEquals(movieId, reservationTicket.movieId)
            assertEquals(movieTitle, reservationTicket.movieTitle)
            assertEquals(amount, reservationTicket.amount)
            assertEquals(theaterId, reservationTicket.theaterId)
            assertEquals(screeningDateTime.time, reservationTicket.screeningDateTime.time)
            assertEquals(screeningDateTime.date, reservationTicket.screeningDateTime.date)
            assertEquals(seats.seats.size, reservationTicket.seats.seats.size)
        }
        assertEquals(1, reservations.size)
    }

    @Test
    fun `티켓의_고유값으로_저장_된_티켓을_찾아오는지_확인한다`() {
        //given
        val reservationTicket = makeMockTicket().toReservationTicket("영화 제목", "극장 이름")
        reservationDao.saveReservationTicket(reservationTicket)
        val actualReservation = reservationDao.findReservations().first()
        val expectedReservation = reservationDao.findReservationById(actualReservation.ticketId)

        //then
        actualReservation.apply {
            assertEquals(movieId, expectedReservation?.movieId)
            assertEquals(movieTitle, expectedReservation?.movieTitle)
            assertEquals(amount, expectedReservation?.amount)
            assertEquals(theaterId, expectedReservation?.theaterId)
            assertEquals(screeningDateTime.time, expectedReservation?.screeningDateTime?.time)
            assertEquals(screeningDateTime.date, expectedReservation?.screeningDateTime?.date)
            assertEquals(seats.seats.size, expectedReservation?.seats?.seats?.size)
        }
    }

    @Test
    fun `저장_된_모든_티켓을_삭제하는지_확인한다`(){
        //given
        val reservationTicket = makeMockTicket().toReservationTicket("영화 제목", "극장 이름")
        reservationDao.saveReservationTicket(reservationTicket)
        val prevReservationsSize = reservationDao.findReservations().size
        reservationDao.clearReservations()
        val afterReservationsSize = reservationDao.findReservations().size

        //then
        assertNotEquals(prevReservationsSize, afterReservationsSize)
    }
}
