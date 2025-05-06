package woowacourse.movie.domain.model

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.MovieDao
import java.time.LocalDate
import java.time.LocalDateTime

class MovieDaoTest {
    private lateinit var movieDao: MovieDao
    private lateinit var dummyMovieDatabase: DummyMovieDatabase

    @BeforeEach
    fun setUp() {
        dummyMovieDatabase = DummyMovieDatabase()
        movieDao = MovieDao(dummyMovieDatabase)
    }

    @Test
    fun `getTheaterNames 메서드는 등록된 상영관 이름 목록을 반환한다`() {
        // given
        val expectedTheaterNames = listOf("선릉")

        // when
        val actualTheaterNames = movieDao.getTheaterNames()

        // then
        assertThat(actualTheaterNames).containsExactlyElementsOf(expectedTheaterNames)
    }

    @Test
    fun `getShowingMovies 메서드는 현재 시간 기준 상영 회차가 남아있는 영화를 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 30, 15, 59)

        // when
        val actualMovies = movieDao.getShowingMovies(now)

        // then
        assertThat(actualMovies).contains(dummyMovieDatabase.movies["라라랜드"])
    }

    @Test
    fun `getShowingMovies 메서드는 현재 시간 기준 상영 회차가 남아있지 않은 영화는 포함시키지 않는다`() {
        // given
        val future = LocalDateTime.of(2025, 5, 1, 16, 0)

        // when
        val futureActualMovies = movieDao.getShowingMovies(future)

        // then
        assertThat(futureActualMovies).isEmpty()
    }

    @Test
    fun `getMovies 메서드는 특정 상영관에서 상영 중인 영화 목록을 반환한다`() {
        // given
        val theaterName = "선릉"
        val expectedMovies =
            listOf(
                dummyMovieDatabase.movies["라라랜드"]!!,
            )

        // when
        val actualMovies = movieDao.getMovies(theaterName)

        // then
        assertThat(actualMovies).containsExactlyInAnyOrderElementsOf(expectedMovies)
    }

    @Test
    fun `getScreenTimes 메서드는 특정 상영관에서 특정 영화의 상영 시간 목록을 반환한다`() {
        // given
        val theaterName = "선릉"
        val movieName = "라라랜드"
        val expectedScreenTimes = listOf(10, 13, 16)

        // when
        val actualScreenTimes = movieDao.getScreenTimes(theaterName, movieName)

        // then
        assertThat(actualScreenTimes).isEqualTo(expectedScreenTimes)
    }

    @Test
    fun `getTimeTable 메서드는 주어진 날짜와 시간에 따라 남아있는 상영 회차 목록을 반환한다`() {
        // given
        val now = LocalDateTime.of(2025, 4, 1, 14, 0)
        val selectedDate = LocalDate.of(2025, 4, 1)
        val screenTimes = listOf(10, 13, 16)
        val expectedTimeTable = listOf(16)

        // when
        val actualTimeTable = movieDao.getTimeTable(now, selectedDate, screenTimes)

        // then
        assertThat(actualTimeTable).isEqualTo(expectedTimeTable)
    }
}
