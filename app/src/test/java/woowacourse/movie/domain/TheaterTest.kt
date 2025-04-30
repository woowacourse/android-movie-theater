package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.fixture.screeningFixtureWithMovieId1
import woowacourse.movie.domain.fixture.screeningFixtureWithMovieId2
import woowacourse.movie.domain.model.theater.Theater

class TheaterTest {
    @Test
    fun `특정 영화의 상영중인 시간 개수를 가져온다`() {
        // when
        val screening =
            listOf(
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId2,
            )

        // given
        val theater = Theater("잠실 극장", screening)

        // then
        val excepted = theater.screeningTimeCount(1)
        assertEquals(excepted, 3)
    }

    @Test
    fun `특정 영화의 상영 스케쥴 정보를 가져온다`() {
        // when
        val screening =
            listOf(
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId2,
            )

        // given
        val theater = Theater("잠실 극장", screening)

        // then
        val excepted = theater.getMovieScreening(1)

        assertEquals(
            excepted,
            listOf(
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId1,
                screeningFixtureWithMovieId1,
            ),
        )
    }
}
