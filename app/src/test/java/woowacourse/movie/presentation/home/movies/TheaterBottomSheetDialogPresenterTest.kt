package woowacourse.movie.presentation.home.movies

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.presentation.model.MovieUiModel
import woowacourse.movie.presentation.model.PosterUiModel
import woowacourse.movie.presentation.model.ScreeningPeriodUiModel
import woowacourse.movie.presentation.model.TheaterUiModel
import woowacourse.movie.presentation.model.TheatersUiModel
import woowacourse.movie.presentation.view.home.movies.dialog.TheaterBottomSheetDialogContract
import woowacourse.movie.presentation.view.home.movies.dialog.TheaterBottomSheetDialogPresenter
import java.time.LocalDate
import java.time.LocalDateTime

class TheaterBottomSheetDialogPresenterTest {
    private lateinit var presenter: TheaterBottomSheetDialogContract.Presenter
    private lateinit var view: TheaterBottomSheetDialogContract.View

    private val fakeMovie =
        MovieUiModel(
            id = 1,
            title = "해리포터",
            poster = PosterUiModel.Url(""),
            screeningPeriod = ScreeningPeriodUiModel(LocalDate.now(), LocalDate.now().plusDays(2)),
            runningTime = 152,
        )

    private val fakeTheaters =
        TheatersUiModel(
            mapOf("선릉 극장" to listOf(LocalDateTime.now().plusDays(1))),
        )

    private val fakeTheater =
        TheaterUiModel(
            name = "선릉 극장",
            times = listOf(LocalDateTime.now()),
        )

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = TheaterBottomSheetDialogPresenter(view)
    }

    @Test
    fun `영화와_영화관들을_불러온다`() {
        // Given
        every { view.showTheaters(any()) } just Runs

        // When
        presenter.fetch(fakeTheaters, fakeMovie)

        // Then
        verify {
            view.showTheaters(any())
        }
    }

    @Test
    fun `특정_영화관을_전달하면_상세_정보를_보여준다`() {
        // Given
        every { view.showTheaters(any()) } just Runs
        every { view.showDetail(any(), any()) } just Runs
        presenter.fetch(fakeTheaters, fakeMovie)

        // When
        presenter.presentTheaterItem(fakeTheater)

        // Then
        verify {
            view.showDetail(any(), any())
        }
    }
}
