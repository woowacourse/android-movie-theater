package woowacourse.movie.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.fixture.MovieFixture
import java.time.LocalDate

class TheaterTest {
    @Test
    fun `극장에서 헤당 영화를 상영 중이면 true를 반환한다`() {
        val theater = Theater("선릉", MovieFixture.MAX_MOVIES)
        val movie = Movie(
            Title("해리포터와 마법사의 돌 1"),
            R.drawable.movie_poster,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 25),
            ),
            152
        )

        val actual = theater.isShowing(movie)

        Assertions.assertThat(actual).isTrue
    }

    @Test
    fun `극장에서 헤당 영화를 상영하지 않으면 false를 반환한다`() {
        val theater = Theater("선릉", MovieFixture.MAX_MOVIES)
        val movie = Movie(
            Title("해리포터"),
            R.drawable.movie_poster,
            ScreeningPeriod(
                LocalDate.of(2025, 4, 1),
                LocalDate.of(2025, 4, 25),
            ),
            152
        )

        val actual = theater.isShowing(movie)

        Assertions.assertThat(actual).isFalse
    }
}
