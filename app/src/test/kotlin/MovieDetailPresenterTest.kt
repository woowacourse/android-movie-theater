import io.mockk.CapturingSlot
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.ScreeningSchedule
import woowacourse.movie.moviedetail.MovieDetailContract
import woowacourse.movie.moviedetail.MovieDetailPresenter
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.MovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toMovieDetailUiModel
import woowacourse.movie.usecase.FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase
import woowacourse.movie.usecase.FetchScreeningsWithMovieIdAndTheaterIdUseCase

@ExtendWith(MockKExtension::class)
class MovieDetailPresenterTest {
    @RelaxedMockK
    private lateinit var view: MovieDetailContract.View

    @MockK
    private lateinit var fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase: FetchScreeningScheduleWithMovieIdAndTheaterIdUseCase

    @MockK
    private lateinit var fetchScreeningsWithMovieIdAndTheaterIdUseCase: FetchScreeningsWithMovieIdAndTheaterIdUseCase

    @InjectMockKs
    private lateinit var presenter: MovieDetailPresenter

    @Test
    @DisplayName("영화 정보를 불러오면 화면에 나타난다")
    fun show_movie_info_When_load_movie_data() {
        val slot = slot<MovieDetailUiModel>()
        every { view.showMovieInfo(capture(slot)) } just runs
        every {
            fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(
                1,
                1,
            )
        } returns Result.success(ScreeningSchedule.STUB_A)

        presenter.loadMovieDetail(1, 1)

        assertThat(ScreeningSchedule.STUB_A.toMovieDetailUiModel()).isEqualTo(slot.captured)
    }

    @Test
    @DisplayName("현재 예매 인원이 1일 때, 플러스 버튼을 누르면 인원이 2가 된다.")
    fun becomes_2_when_current_number_is_1_And_click_plus_button() {
        presenter.plusCount()

        val updatedCount = HeadCountUiModel("2")
        verify(exactly = 1) { view.updateHeadCount(updatedCount) }
    }

    @Test
    @DisplayName("현재 예매 인원이 2일 때, 마이너스 버튼을 누르면 인원이 1이 된다.")
    fun becomes_1_when_current_number_is_2_And_click_minus_button() {
        presenter.plusCount()

        presenter.minusCount()

        val updatedCount = HeadCountUiModel("1")
        verify(exactly = 1) { view.updateHeadCount(updatedCount) }
    }

    @Test
    @DisplayName("현재 예매 인원이 1일 때, 마이너스 버튼을 눌러도 감소하지 않는다.")
    fun does_not_decrease_When_current_number_is_1_And_click_minus_button() {
        val currentCount = HeadCountUiModel("1")

        presenter.minusCount()

        verify(exactly = 1) { view.updateHeadCount(currentCount) }
    }

    @Test
    fun `예매 날짜를 선택하면 예매 시간 목록이 나온다`() {
        val slot = CapturingSlot<List<String>>()
        every { view.showTime(capture(slot)) } just runs
        every {
            fetchScreeningScheduleWithMovieIdAndTheaterIdUseCase(
                1,
                1,
            )
        } returns Result.success(ScreeningSchedule.STUB_A)
        presenter.loadMovieDetail(1, 1)

        presenter.selectDate(0)
        val expected = listOf("09:00", "10:00", "11:00", "12:00")
        val actual = slot.captured
        assertThat(actual).isEqualTo(expected)
    }
}
