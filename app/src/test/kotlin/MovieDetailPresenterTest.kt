import io.mockk.Runs
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.DummyMovieRepository
import woowacourse.movie.model.Screening
import woowacourse.movie.moviedetail.MovieDetailContract
import woowacourse.movie.moviedetail.MovieDetailPresenter
import woowacourse.movie.moviedetail.uimodel.HeadCountUiModel
import woowacourse.movie.moviedetail.uimodel.MovieDetailUiModel
import woowacourse.movie.moviedetail.uimodel.toMovieDetailUiModel

@ExtendWith(MockKExtension::class)
class MovieDetailPresenterTest {
    @MockK
    private lateinit var view: MovieDetailContract.View

    private lateinit var presenter: MovieDetailContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = MovieDetailPresenter(view, DummyMovieRepository)
    }

    @Test
    @DisplayName("영화 정보를 불러오면 화면에 나타난다")
    fun show_movie_info_When_load_movie_data() {
        val slot = slot<MovieDetailUiModel>()
        every { view.showBookingDetail(any(), any()) } just Runs
        every { view.showMovieInfo(capture(slot)) } just Runs

        presenter.loadMovieDetail(0)

        verify(exactly = 1) { view.showMovieInfo(any()) }
        assertThat(Screening.STUB_A.toMovieDetailUiModel()).isEqualTo(slot.captured)
    }

    @Test
    @DisplayName("현재 예매 인원이 1일 때, 플러스 버튼을 누르면 인원이 2가 된다.")
    fun becomes_2_when_current_number_is_1_And_click_plus_button() {
        val updatedCount = HeadCountUiModel("2")
        every { view.updateHeadCount(updatedCount) } just Runs

        val currentCount = HeadCountUiModel("1")
        presenter.plusCount()

        verify(exactly = 1) { view.updateHeadCount(updatedCount) }
    }

    @Test
    @DisplayName("현재 예매 인원이 2일 때, 마이너스 버튼을 누르면 인원이 1이 된다.")
    fun becomes_1_when_current_number_is_2_And_click_minus_button() {
        val updatedCount = HeadCountUiModel("1")
        every { view.updateHeadCount(updatedCount) } just Runs

        val currentCount = HeadCountUiModel("2")
        presenter.minusCount()

        verify(exactly = 1) { view.updateHeadCount(updatedCount) }
    }

    @Test
    @DisplayName("현재 예매 인원이 1일 때, 마이너스 버튼을 눌러도 감소하지 않는다.")
    fun does_not_decrease_When_current_number_is_1_And_click_minus_button() {
        val updatedCount = HeadCountUiModel("1")
        every { view.updateHeadCount(updatedCount) } just Runs

        val currentCount = HeadCountUiModel("1")
        presenter.minusCount()

        verify(exactly = 1) { view.updateHeadCount(currentCount) }
    }
}
