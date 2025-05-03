package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.fixture.gangNamTheaterFixture
import woowacourse.movie.domain.fixture.suwonTheaterFixture
import java.time.LocalDateTime

class TheaterTest {
    @Test
    fun `특정 영화의 상영중인 시간 개수를 가져온다`() {
        // when
        val theater = gangNamTheaterFixture

        // given
        val expected = theater.screeningTimeCount(1)

        // then
        assertEquals(expected, 1)
    }

    @Test
    fun `특정 영화의 상영 스케쥴 정보를 모두 가져온다`() {
        // when
        val theater = suwonTheaterFixture

        // given
        val excepted = theater.getMovieScreening(1)

        // then
        assertEquals(
            excepted,
            listOf(LocalDateTime.of(2025, 4, 29, 9, 30)),
        )
    }
}
