package woowacourse.movie.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.fixture.MovieFixture
import java.time.LocalDate

class TheatersTest {
    @Test
    fun `영화관들은 해당 영화를 상영하는 영화관 리스트를 반환할 수 있다`() {
        val theater = Theater("선릉", MovieFixture.MAX_MOVIES)
        val theaters = Theaters(listOf(theater))
        val movie =
            Movie(
                Title("해리포터와 마법사의 돌 1"),
                R.drawable.movie_poster,
                ScreeningPeriod(
                    LocalDate.of(2025, 4, 1),
                    LocalDate.of(2025, 4, 25),
                ),
                152,
            )
        val actual = theaters.filterByMovie(movie)
        val expected = listOf(theater)

        Assertions.assertThat(actual).isEqualTo(expected)
    }
}
