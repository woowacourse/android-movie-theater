package woowacourse.movie.data.dao

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.kotest.assertions.assertSoftly
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.DisplayName
import woowacourse.movie.data.db.MovieDatabase
import woowacourse.movie.data.reservationEntity
import woowacourse.movie.data.seatEntity

class MovieDaoTest {

    private lateinit var movieDatabase: MovieDatabase
    private lateinit var movieDao: MovieDao

    @Before
    fun setUp() {
        movieDatabase = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            MovieDatabase::class.java
        ).build()
        movieDao = movieDatabase.movieDao()
    }

    @After
    fun teardown() {
        movieDatabase.close()
    }

    @Test
    @DisplayName("MovieDB 에 예매 내역을 저장할 수 있다.")
    fun insert_test() {
        //given
        val reservationEntity = reservationEntity()
        //when
        val id = movieDao.saveReservation(reservationEntity)
        //then
        id shouldBe 1L
    }

    @Test
    @DisplayName("예매 내역을 저장 하고, id로 영화 내역을 조회할 수 있다.")
    fun insert_and_load_test() {
        repeat(1000) {
            //given
            val reservationEntity = reservationEntity().copy(cinemaName = "$it")
            //when
            val id: Long = movieDao.saveReservation(reservationEntity)
            val reservationResult = movieDao.loadReservation(id)
            //then
            assertSoftly {
                id shouldBe (it + 1L)
                reservationResult shouldBe reservationEntity
            }
        }
    }

    @Test
    @DisplayName("예매 내역 저장후 예매 id를 기반 으로 좌석들 저장 후, 예매 id로 영화 내역 조회")
    fun insert_reservation_and_seats_test() {
        // given
        val reservationEntity = reservationEntity()
        // when
        val reservationId = movieDao.saveReservation(reservationEntity)
        val seatEntities = listOf(
            seatEntity(reservationId, 1, 2),
            seatEntity(reservationId, 2, 2),
        )
        movieDao.saveMovieSeats(seatEntities)
        val reservationWithSeats = movieDao.loadReservationWithSeats(1)
        // then
        assertSoftly {
            reservationEntity shouldBeIn reservationWithSeats.keys
            reservationWithSeats[reservationEntity] shouldBe seatEntities
        }
    }
}