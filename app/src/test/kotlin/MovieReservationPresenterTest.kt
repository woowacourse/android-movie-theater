import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.model.HeadCount
import woowacourse.movie.model.ScreeningMovie
import woowacourse.movie.moviereservation.MovieReservationContract
import woowacourse.movie.moviereservation.MovieReservationPresenter
import woowacourse.movie.moviereservation.toMovieReservationUiModel
import woowacourse.movie.moviereservation.toScreeningDateTimeUiModel
import woowacourse.movie.moviereservation.uimodel.CurrentBookingDetail
import woowacourse.movie.moviereservation.uimodel.MovieReservationUiModel

class MovieReservationPresenterTest {
    private lateinit var view: MovieReservationContract.View

    private lateinit var presenter: MovieReservationPresenter

    @BeforeEach
    fun setUp() {
        view = mockk<MovieReservationContract.View>()
        presenter = MovieReservationPresenter(view, DummyMovies)
    }

    @Test
    @DisplayName("영화 정보를 불러오는데 실패하면, 데이터가 정제되어 뷰에 전달된다.")
    fun send_movie_info_When_load_movie_data() {
        // when
        val screeningMovie = ScreeningMovie.STUB_A

        val expectedReservationUiModel: MovieReservationUiModel =
            screeningMovie.toMovieReservationUiModel()
        val expectedScreeningDateTimeUiModel = screeningMovie.toScreeningDateTimeUiModel()
        val expectedCurrentBookingDetail = CurrentBookingDetail(HeadCount.MIN_COUNT)

        every {
            view.showMovieInfo(expectedReservationUiModel)
            view.showBookingDetail(
                expectedScreeningDateTimeUiModel,
                expectedCurrentBookingDetail,
            )
        } just Runs

        // given
        presenter.loadMovieDetail(0)
        assertThat(DummyMovies.screenMovieById(0)).isEqualTo(screeningMovie)

        // then
        verify(exactly = 1) {
            view.showMovieInfo(expectedReservationUiModel)
            view.showBookingDetail(expectedScreeningDateTimeUiModel, expectedCurrentBookingDetail)
        }
    }

    @Test
    @DisplayName("영화 정보를 불러오는데 실패하면, 에러에 관한 뷰를 호출한다.")
    fun call_error_view_When_fail_load_movie() {
        // when
        every { view.showScreeningMovieError() } just Runs

        // given
        presenter.loadMovieDetail(-1)

        // then
        verify(exactly = 1) { view.showScreeningMovieError() }
    }

    @Test
    @DisplayName("현재 예매 인원이 1일 때, 플러스 버튼을 누르면 2를 전달한다.")
    fun becomes_2_when_current_number_is_1_And_click_plus_button() {
        every { view.updateHeadCount(2) } just Runs

        presenter.plusCount(1)

        verify(exactly = 1) { view.updateHeadCount(2) }
    }

    @Test
    @DisplayName("현재 예매 인원이 2일 때, 마이너스 버튼을 누르면 1을 전달한다.")
    fun becomes_1_when_current_number_is_2_And_click_minus_button() {
        every { view.updateHeadCount(1) } just Runs

        presenter.minusCount(2)

        verify(exactly = 1) { view.updateHeadCount(1) }
    }

    @Test
    @DisplayName("현재 예매 인원이 1일 때, 마이너스 버튼을 눌러도 감소하지 않는다.")
    fun does_not_decrease_When_current_number_is_1_And_click_minus_button() {
        every { view.showCantDecreaseError(1) } just Runs

        presenter.minusCount(1)

        verify(exactly = 1) { view.showCantDecreaseError(1) }
        verify(exactly = 0) { view.updateHeadCount(any()) }
    }
}
