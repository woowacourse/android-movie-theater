package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.theater.Theater
import woowacourse.movie.fixture.MOVIE_1_SCREENING
import woowacourse.movie.fixture.MOVIE_2_SCREENING

class TheaterTest {
    @Test
    fun `특정 영화의 상영중인 시간 개수를 가져온다`() {
        // when
        val screening =
            listOf(
                MOVIE_1_SCREENING,
                MOVIE_1_SCREENING,
                MOVIE_1_SCREENING,
                MOVIE_2_SCREENING,
            )

        // given
        val theater = Theater("잠실 극장", screening)

        // then
        val excepted = theater.screeningsCount(1)
        assertEquals(excepted, 3)
    }

    @Test
    fun `특정 영화의 상영 스케쥴 정보를 가져온다`() {
        // when
        val screening =
            listOf(
                MOVIE_1_SCREENING,
                MOVIE_1_SCREENING,
                MOVIE_1_SCREENING,
                MOVIE_2_SCREENING,
            )

        // given
        val theater = Theater("잠실 극장", screening)

        // then
        val excepted = theater.screeningTimes(1)

        assertEquals(
            excepted,
            listOf(
                MOVIE_1_SCREENING.time,
                MOVIE_1_SCREENING.time,
                MOVIE_1_SCREENING.time,
            ),
        )
    }
}
