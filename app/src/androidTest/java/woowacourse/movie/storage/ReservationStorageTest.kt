package woowacourse.movie.storage

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.MOVIE_TICKET_B1_C3
import woowacourse.movie.MOVIE_TICKET_ENTITY_B1_C3
import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.data.storage.DefaultReservationStorage
import woowacourse.movie.data.storage.ReservationStorage
import woowacourse.movie.fakeContext
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class ReservationStorageTest {
    private lateinit var database: AppDatabase
    private lateinit var storage: ReservationStorage

    @BeforeEach
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(fakeContext, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        storage = DefaultReservationStorage(database)
    }

    @Test
    fun `영화_티켓을_저장하면_콜백을_호출하고_예약_ID를_반환한다`() {
        // given
        var actualReservationId: Long? = null
        val latch = CountDownLatch(1)

        // when
        storage.saveMovieTicket(MOVIE_TICKET_B1_C3) { reservationId ->
            actualReservationId = reservationId
        }

        latch.await(3, TimeUnit.SECONDS)

        // then
        val savedMovieTicket =
            database.reservationDao().getMovieTicketByReservationId(actualReservationId!!)
        assertThat(savedMovieTicket).isEqualTo(MOVIE_TICKET_ENTITY_B1_C3)
        assertThat(actualReservationId).isNotNull
    }

    @Test
    fun 예약_ID값으로_영화_티켓_정보를_가져오면_콜백을_호출하고_해당_정보를_반환한다() {
        // given:
        var actualMovieTicket: MovieTicketEntity? = null
        val latch = CountDownLatch(1)
        val reservationId = 1L

        // when:
        storage.getMovieTicket(reservationId) { movieTicket ->
            actualMovieTicket = movieTicket
        }
        latch.await(3, TimeUnit.SECONDS)

        // then:
        val savedMovieTicket =
            database.reservationDao().getMovieTicketByReservationId(reservationId)
        assertThat(actualMovieTicket).isEqualTo(savedMovieTicket)
    }

    @Test
    fun 모든_영화_티켓들을_가져오면_콜백을_호출하고_해당_정보를_반환한다() {
        // given:
        var actualMovieTickets: List<MovieTicketEntity>? = null
        val latch = CountDownLatch(1)

        // when:
        storage.getAllMovieTickets { movieTickets ->
            actualMovieTickets = movieTickets
        }
        latch.await(3, TimeUnit.SECONDS)

        // then:
        val savedMovieTickets =
            database.reservationDao().getAllMovieTickets()
        assertThat(actualMovieTickets).isEqualTo(savedMovieTickets)
    }

    @AfterEach
    fun finish() {
        database.close()
    }
}
