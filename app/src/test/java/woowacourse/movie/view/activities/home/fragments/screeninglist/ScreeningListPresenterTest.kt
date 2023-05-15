package woowacourse.movie.view.activities.home.fragments.screeninglist

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
import java.time.LocalDate

class ScreeningListPresenterTest {

    private lateinit var view: ScreeningListContract.View
    private lateinit var screeningRepository: ScreeningRepository
    private val screeningId = 1L
    private val fakeTheater: Theater = Theater(5, 4)
    private val fakeScreening: Screening = Screening(
        ScreeningRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
        fakeTheater,
        Movie("title", Minute(152), "summary")
    ).apply { this.id = screeningId }
    private lateinit var sut: ScreeningListPresenter

    @Before
    fun setUp() {
        view = mockk()
        screeningRepository = mockk()
        sut = ScreeningListPresenter(view, screeningRepository)
    }

    @Test
    fun `상영 목록을 로드하면 뷰에 상영 목록 UI 상태를 설정한다`() {
        every { screeningRepository.findAll() } returns listOf(fakeScreening)
        val uiState = ScreeningListUIState.from(listOf(fakeScreening))
        every { view.setScreeningList(uiState) } just runs

        sut.loadScreenings()

        verify { view.setScreeningList(uiState) }
    }
}