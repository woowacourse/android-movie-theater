package woowacourse.movie.model.theater

import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import woowacourse.movie.presenter.THEATER_MOVIE_SCHEDULES
import woowacourse.movie.presenter.THEATER_MOVIE_SCHEDULE_MOVIE_ID_1L

class TheaterMovieSchedulesTest {
    @Test
    fun `영화 id에 맞는 영화관들의 영화 상영 정보를 반환한다`() {
        // given:
        val theaterMovieSchedules: TheaterMovieSchedules = THEATER_MOVIE_SCHEDULES
        val movieId = 1L

        // when:
        val actual = theaterMovieSchedules.findTheaterMovieSchedulesById(movieId)

        // then:
        actual shouldBe THEATER_MOVIE_SCHEDULE_MOVIE_ID_1L
    }
}
