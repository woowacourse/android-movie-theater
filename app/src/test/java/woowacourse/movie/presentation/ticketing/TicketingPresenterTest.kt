package woowacourse.movie.presentation.ticketing

import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.R
import woowacourse.movie.model.Count
import woowacourse.movie.model.Movie
import woowacourse.movie.model.ScreeningDates
import woowacourse.movie.repository.DummyTheaterList
import woowacourse.movie.repository.MovieRepository
import java.time.LocalDate

private val dummyMovies =
    listOf(
        Movie(
            id = 0L,
            title = "해리 포터와 마법사의 돌",
            thumbnail = R.drawable.movie1,
            screeningDates = ScreeningDates(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
            runningTime = 152,
            introduction =
                """
                《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.
                """.trimIndent(),
        ),
    )

@ExtendWith(MockKExtension::class)
class TicketingPresenterTest {
    @RelaxedMockK
    private lateinit var view: TicketingActivity

    @MockK
    private lateinit var repository: MovieRepository

    private lateinit var presenter: TicketingPresenter

    @BeforeEach
    fun setUp() {
        presenter = TicketingPresenter(view, repository, DummyTheaterList)
    }

    @Test
    fun `유효한_movieId를_전달받았을_경우_초기_화면_세팅을_진행한다`() {
        val expectedMovie = dummyMovies[0]
        val expectedDates =
            listOf(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 3))
        val expectedCount = Count()

        every { repository.findMovieById(any()) } returns Result.success(dummyMovies[0])

        presenter.loadMovieData(1L, 1L)

        assertAll(
            { verify { view.displayMovieDetail(expectedMovie) } },
            { verify { view.setUpDateSpinners(expectedDates) } },
            { verify { view.bindTicketCount(expectedCount) } },
        )
    }

    @Test
    fun `유효하지 않은 movieId를 전달받았을 경우, view 에게 에러 메세지를 전달한다`() {
        every { repository.findMovieById(any()) } returns Result.failure(Exception())
        every { view.showErrorMessage(any()) } just Runs

        presenter.loadMovieData(-1L, -1L)

        verify { view.showErrorMessage(any()) }
    }

    @Test
    fun `increaseCount 호출 시, view에게 변경된 티켓 개수를 전달한다`() {
        presenter.increaseCount()

        verify { view.updateTicketCount() }
    }

    @Test
    fun `decreaseCount 호출 시, view에게 변경된 티켓 개수를 전달한다`() {
        presenter.increaseCount()
        presenter.decreaseCount()

        verify { view.updateTicketCount() }
    }

    @Test
    fun `navigate 호출 시, view에게 다음 화면으로 이동하라고 명령한다`() {
        every { repository.findMovieById(any()) } returns Result.success(dummyMovies[0])

        presenter.loadMovieData(1L, 1L)
        presenter.navigate()

        verify { view.navigate(any(), any(), any()) }
    }
}
