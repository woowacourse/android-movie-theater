package woowacourse.movie.presentation.theater

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.ScreeningRepository
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.SEOLLEUNG
import java.time.LocalTime

class TheaterPresenterTest {
    private lateinit var view: TheaterContract.View
    private lateinit var presenter: TheaterContract.Presenter
    private lateinit var screeningData: ScreeningRepository

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        screeningData = mockk(relaxed = true)
        presenter = TheaterPresenter(view, HARRY_POTTER, screeningData)
    }

    @Test
    fun `극장 목록을 가져와서 화면에 출력한다`() {
        // when
        presenter.loadTheaterList()

        // then
        verify { view.showTheaters(any()) }
    }

    @Test
    fun `극장을 선택하면 화면을 이동한다`() {
        // given
        val screening =
            Screening(
                SEOLLEUNG,
                HARRY_POTTER,
                listOf(LocalTime.of(12, 0)),
            )

        // when
        presenter.selectTheater(screening)

        // then
        verify { view.navigateToBooking(screening) }
    }
}
