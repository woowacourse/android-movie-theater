import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.ScreeningSchedule
import woowacourse.movie.model.Theater
import woowacourse.movie.movielist.theaters.TheaterContract
import woowacourse.movie.movielist.theaters.TheaterPresenter
import woowacourse.movie.movielist.theaters.toTheaterUiModel
import woowacourse.movie.usecase.FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.usecase.FetchTheatersWithMovieIdUseCase

@ExtendWith(MockKExtension::class)
class TheaterPresenterTest {
    @RelaxedMockK
    private lateinit var view: TheaterContract.View

    @MockK
    private lateinit var fetchTheatersWithMovieIdUseCase: FetchTheatersWithMovieIdUseCase

    @MockK
    private lateinit var fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase: FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase

    @InjectMockKs
    private lateinit var presenter: TheaterPresenter

    @Test
    fun `선택한 영화의 상영관과 총 상영 시간 개수를 불러온다`() {
        val theaterList = listOf(Theater.STUB_A)
        val screeningSchedule = ScreeningSchedule.STUB_A
        // given
        every { fetchTheatersWithMovieIdUseCase(1) } returns Result.success(theaterList)
        every {
            fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(
                1,
                any(),
            )
        } returns Result.success(screeningSchedule)
        // when
        presenter.loadTheaters(1)
        // then
        verify { view.showTheaters(theaterList.map { it.toTheaterUiModel(screeningSchedule.totalScreeningTimesNum()) }) }
    }

    @Test
    fun `상영관을 선택하면 영화 예매 세부 화면으로 넘어간다`() {
        val screeningSchedule = ScreeningSchedule.STUB_A
        // given
        every { view.navigateToMovieDetail(1, 1) } just runs
        every { fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(1, 1) } returns
            Result.success(
                screeningSchedule,
            )
        // when
        presenter.selectTheater(1, 1)
        // then
        verify { view.navigateToMovieDetail(1, 1) }
    }
}
