package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.theater.Theaters
import java.time.LocalDate
import java.time.LocalTime

class TheatersTest {
    @Test
    fun `극장들에서 영화와 날짜, 시간 기준으로 상영가능한 극장들은 상영 일정들을 갖는다`() {
        // given
        val theaters =
            Theaters(
                listOf(
                    THEATER_CGV_GANGNAM,
                    THEATER_LOTTE_SINEMA,
                    THEATER_MEGA_BOX,
                ),
            )
        val movie = MOVIE_HARRY_POTTER_AND_THE_PHILOSOPHERS_STONE
        val date = LocalDate.of(2025, 4, 10)
        val time = LocalTime.of(0, 0)

        val expectedMovieScheduleSizeOfGangNam = 1
        val expectedMovieScheduleSizeOfOthers = 0

        // when
        val actual: Theaters = theaters.bookableTheaters(movie, date, time)

        // then
        assertThat(actual.theaters[0].schedules.size).isEqualTo(
            expectedMovieScheduleSizeOfGangNam,
        )
        assertThat(actual.theaters[1].schedules.size).isEqualTo(
            expectedMovieScheduleSizeOfOthers,
        )
        assertThat(actual.theaters[2].schedules.size).isEqualTo(
            expectedMovieScheduleSizeOfOthers,
        )
    }
}
