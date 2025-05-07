package woowacourse.movie.domain

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.fixture.allTheaters
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDateTime

class TheatersTest {
    @Test
    fun `특정 영화가 상영중인 극장 목록을 반환한다`() {
        // when
        val theaters = Theaters(allTheaters)

        // given
        val expected = theaters.bookingAbleTheater(1).map { it.name }

        // then
        assertEquals(expected, listOf("강남 극장", "서울 극장"))
    }

    @Test
    fun `특정 극장의 특정 영화가 상영중인 일정을 반환한다`() {
        // when
        val theaters = Theaters(allTheaters)

        // given
        val expected = theaters.selectedMovieScreeningTimes(1, "서울 극장")

        // then
        assertEquals(
            expected,
            listOf(
                LocalDateTime.of(2025, 4, 29, 9, 30),
            ),
        )
    }
}
