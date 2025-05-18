package woowacourse.movie.dao

import androidx.room.Room
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import woowacourse.movie.MOVIE_TICKET_ENTITY_B1_C3
import woowacourse.movie.data.db.AppDatabase
import woowacourse.movie.data.entity.MovieTicketEntity
import woowacourse.movie.fakeContext

class ReservationDaoTest {
    private lateinit var database: AppDatabase

    @BeforeEach
    fun setup() {
        database =
            Room
                .inMemoryDatabaseBuilder(fakeContext, AppDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @Test
    fun 모든_영화_티켓_정보를_가져온다() {
        // given:
        database.reservationDao().insertMovieTicketEntity(MOVIE_TICKET_ENTITY_B1_C3)

        // when:
        val actual: List<MovieTicketEntity> = database.reservationDao().getAllMovieTickets()

        // then:
        assertThat(actual).isEqualTo(listOf(MOVIE_TICKET_ENTITY_B1_C3))
    }

    @Test
    fun 예약_정보를_삽입하고_조회할_수_있다() {
        // given:
        val id: Long = database.reservationDao().insertMovieTicketEntity(MOVIE_TICKET_ENTITY_B1_C3)

        // when:
        val actual: MovieTicketEntity? = database.reservationDao().getMovieTicketByReservationId(id)

        // then:
        assertAll({
            assertThat(actual).isEqualTo(MOVIE_TICKET_ENTITY_B1_C3)
            assertThat(actual?.reservationInfoEntity?.id).isEqualTo(id)
        })
    }

    @Test
    fun 존재하지_않는_예약_ID는_예약_정보_null을_반환한다() {
        // given:
        // when:
        val actual = database.reservationDao().getMovieTicketByReservationId(999_999_999_999L)

        // then:
        assertThat(actual).isNull()
    }

    @AfterEach
    fun finish() {
        database.close()
    }
}
