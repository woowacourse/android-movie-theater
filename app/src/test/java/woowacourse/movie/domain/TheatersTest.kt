package woowacourse.movie.domain

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.MovieFixture

class TheatersTest {
    @Test
    fun `영화관들은 해당 영화를 상영하는 영화관 리스트를 반환할 수 있다`() {
        val theater = Theater("선릉", MovieFixture.THEATER_MOVIES, MovieFixture.THEATER_TIMETABLE)
        val theaters = Theaters(listOf(theater))
        val movie = MovieFixture.THEATER_MOVIE
        val actual = theaters.filterByMovie(movie)
        val expected = listOf(theater)

        Assertions.assertThat(actual).isEqualTo(expected)
    }
}
