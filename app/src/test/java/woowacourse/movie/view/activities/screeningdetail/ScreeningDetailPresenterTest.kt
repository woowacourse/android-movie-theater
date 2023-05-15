package woowacourse.movie.view.activities.screeningdetail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.screening.Minute
import woowacourse.movie.domain.screening.Movie
import woowacourse.movie.domain.screening.Screening
import woowacourse.movie.domain.screening.ScreeningRange
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.repository.ScreeningRepository
import woowacourse.movie.view.PosterResourceProvider
import java.time.LocalDate

class ScreeningDetailPresenterTest {

    private lateinit var view: ScreeningDetailContract.View

    private lateinit var screeningRepository: ScreeningRepository

    private val screeningId = 1L

    private val fakeTheater: Theater = Theater(5, 4)

    private val fakeScreening: Screening = Screening(
        ScreeningRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
        fakeTheater,
        Movie("title", Minute(152), "summary")
    ).apply { this.id = screeningId }

    private lateinit var sut: ScreeningDetailPresenter

    @Before
    fun setUp() {
        view = mockk()
        screeningRepository = mockk()
        sut = ScreeningDetailPresenter(view, screeningId, screeningRepository)
    }

    @Test
    fun `상영을 로드하면 뷰에 상영 상세 UI 상태를 설정한다`() {
        every { screeningRepository.findById(screeningId) } returns fakeScreening
        val posterResourceId = PosterResourceProvider.getPosterResourceId(fakeScreening)
        val uiState = ScreeningDetailUIState.of(fakeScreening, posterResourceId)
        every { view.setScreening(uiState) } just runs

        sut.loadScreeningData()

        verify { view.setScreening(uiState) }
    }
}