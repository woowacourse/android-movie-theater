package woowacourse.movie.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import woowacourse.movie.fixture.MovieFixture

class TheaterTest {
    @Test
    fun `극장에서 헤당 영화를 상영 중이면 true를 반환한다`() {
        val theater = Theater("선릉", MovieFixture.THEATER_MOVIES, MovieFixture.THEATER_TIMETABLE)
        val movie = MovieFixture.THEATER_MOVIE

        val actual = theater.isShowing(movie)

       assertThat(actual).isTrue
    }

    @Test
    fun `극장에서 헤당 영화를 상영하지 않으면 false를 반환한다`() {
        val theater = Theater("선릉", MovieFixture.THEATER_MOVIES, MovieFixture.THEATER_TIMETABLE)
        val movie = MovieFixture.THEATER_MOVIE_IS_NOT

        val actual = theater.isShowing(movie)

        assertThat(actual).isFalse
    }

    @Test
    fun `극장은 해당 영화 제목의 타임테이블을 반환한다`() {
        val theater = Theater("선릉", MovieFixture.THEATER_MOVIES, MovieFixture.THEATER_TIMETABLE)
        val movie = MovieFixture.THEATER_MOVIE

        val actual = theater.movieTimeTable(movie)
        val expected = MovieFixture.THEATER_TIMETABLE[Title(movie.title)]
        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `극장은 해당 영화 제목을 찾을 수 없으면 빈 리스트를 반환한다`() {
        val theater = Theater("선릉", MovieFixture.THEATER_MOVIES, MovieFixture.THEATER_TIMETABLE)
        val movie = MovieFixture.THEATER_MOVIE_IS_NOT

        val actual = theater.movieTimeTable(movie)

        assertThat(actual.size).isEqualTo(0)
    }
}
