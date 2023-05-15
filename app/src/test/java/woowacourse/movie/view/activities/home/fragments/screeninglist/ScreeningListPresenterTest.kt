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
import woowacourse.movie.domain.screening.Screening1
import woowacourse.movie.domain.screening.ScreeningRange
import woowacourse.movie.domain.screening.TimeTable
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.repository.Screening1Repository
import woowacourse.movie.view.activities.home.fragments.screeninglist.uistates.TheatersUIState
import java.time.LocalDate
import java.time.LocalTime

class ScreeningListPresenterTest {

    private lateinit var view: ScreeningListContract.View
    private lateinit var screeningRepository: Screening1Repository
    private val screeningId = 1L
    private val fakeTheater: Theater = Theater(5, 4).apply { id = 1L }
    private val fakeScreening: Screening1 = Screening1(
        ScreeningRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 31)),
        TimeTable(mapOf(fakeTheater to listOf(LocalTime.of(20, 0)))),
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

    @Test
    fun `지금 예매 시 영화를 상영하는 극장을 보여준다`() {
        every { screeningRepository.findById(screeningId) } returns fakeScreening
        val uiState = TheatersUIState.from(fakeScreening)
        every { view.showTheaters(uiState) } just runs

        sut.onReserveNow(screeningId)

        verify { view.showTheaters(uiState) }
    }
}