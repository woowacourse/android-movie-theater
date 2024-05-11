package woowacourse.movie.presentation.movieDetail

import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import io.mockk.verifyAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
class MovieDetailPresenterTest {
    @RelaxedMockK
    private lateinit var view: MovieDetailContract.View

    @InjectMockKs
    private lateinit var presenter: MovieDetailPresenter

    private var cinema =
        Cinema(
            "CGV",
            Theater(
                MovieInfo(
                    Title("차람과 하디의 진지한 여행기 1"),
                    MovieDate(LocalDate.of(2024, 2, 25)),
                    RunningTime(230),
                    Synopsis("wow!"),
                ),
                times =
                    listOf(
                        LocalTime.of(10, 0),
                        LocalTime.of(14, 0),
                        LocalTime.of(18, 0),
                    ),
                seats = mapOf(),
            ),
        )

    @Test
    fun `티켓을 하나 증가시키면 값이 반영되어 view의 onTicketChanged로 전달되는지 테스트`() {
        presenter.increaseTicketCount()

        verify { view.onTicketCountChanged(2) }
    }

    @Test
    fun `티켓을 하나 감소시키면 값이 반영되어 view의 onTicketChanged로 전달되는지 테스트`() {
        presenter.increaseTicketCount()
        presenter.increaseTicketCount()
        presenter.decreaseTicketCount()

        verify { view.onTicketCountChanged(1) }
    }

    @Test
    fun `updateRunMovieTimes를 호출시키면 view의 updateTimeAdapter로 영화 시간 데이터가 전달되는지 확인`() {
        val expected = cinema.theater.times.map { it.toString() }

        presenter.updateRunMovieTimes()

        verify { view.updateTimeAdapter(expected) }
    }

    @Test
    fun `loadMovieInfo를 호출하면 view에 movie 데이터들이 반영되는지 테스트`() {
        val expectedMovie = cinema.theater.movie
        val expectedCount = 1

        presenter.loadMovieInfo()

        verifyAll {
            with(view) {
                onTicketCountChanged(expectedCount)
                showTitle(expectedMovie.title)
                showSynopsis(expectedMovie.synopsis)
                showReleaseDate(expectedMovie.releaseDate)
                showRunningTime(expectedMovie.runningTime)
            }
        }
    }
}
