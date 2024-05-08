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
import woowacourse.movie.repository.MovieRepository

@ExtendWith(MockKExtension::class)
class TheaterPresenterTest {
    @RelaxedMockK
    private lateinit var view: TheaterContract.View

    @MockK
    private lateinit var repository: MovieRepository

    @InjectMockKs
    private lateinit var presenter: TheaterPresenter

    @Test
    fun `선택한 영화의 상영관과 총 상영 시간 개수를 불러온다`() {
        val theaterList = listOf(Theater.STUB_A)
        val screeningSchedule = ScreeningSchedule.STUB_A
        // given
        every { repository.theatersByMovieId(0) } returns theaterList
        every { repository.screeningScheduleByMovieIdAndTheaterId(0, any()) } returns ScreeningSchedule.STUB_A
        // when
        presenter.loadTheaters(0)
        // then
        verify { view.showTheaters(theaterList.map { it.toTheaterUiModel(screeningSchedule.totalScreeningTimesNum()) }) }
    }

    @Test
    fun `상영관을 선택하면 영화 예매 세부 화면으로 넘어간다`() {
        // given
        every { view.navigateToMovieDetail(0) } just runs
        every { repository.screeningScheduleByMovieIdAndTheaterId(0, 0) } returns ScreeningSchedule.STUB_A
        // when
        presenter.selectTheater(0, 0)
        // then
        verify { view.navigateToMovieDetail(0) }
    }
}
