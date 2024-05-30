package woowacourse.movie.ui.detail

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.FakeMovieDataSource
import woowacourse.movie.data.repository.FakeReservationRepository
import woowacourse.movie.data.repository.FakeScreenDataSource
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.repository.DefaultMovieRepository
import woowacourse.movie.domain.repository.DefaultScreenRepository
import woowacourse.movie.ui.MovieDetailUI
import woowacourse.movie.ui.ScreenDetailUi
import java.time.LocalDate

class ScreenDetailPresenterTest {
    private lateinit var mockView: ScreenDetailContract.View
    private lateinit var presenter: ScreenDetailContract.Presenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<ScreenDetailContract.View>(relaxed = true)
    }

    private val fakeScreenDetailUI =
        ScreenDetailUi(
            id = 1,
            movieDetailUI =
                MovieDetailUI(
                    title = "title1",
                    runningTime = 1,
                    description = "description1",
                    image = Image.StringImage("1"),
                ),
            dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
        )

    @Test
    fun `영화 정보를 표시2`() {
        // given
        presenter = testScreenDetailPresenterFixture2(screenId = 1, theaterId = 1)

        // when
        presenter.loadScreen()

        // then
        verify(exactly = 1) { mockView.showScreen(fakeScreenDetailUI) }
        verify(exactly = 1) { mockView.showDateTimePicker(any(), any(), any(), any()) }
    }

    @Test
    fun `첫 화면에서 티켓의 개수는 1이다2`() {
        // given
        presenter = testScreenDetailPresenterFixture2()

        // when
        presenter.loadTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(1) }
    }

    @Test
    fun `처음 화면에서 + 버튼을 누르면 티켓의 개수는 2이다2`() {
        // given
        presenter = testScreenDetailPresenterFixture2()

        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(2) }
    }

    @Test
    fun `처음 화면에서 - 버튼을 누르면 티켓의 개수는 1이다2`() {
        // given
        presenter = testScreenDetailPresenterFixture2()

        // when
        presenter.minusTicket()

        // then
        verify(exactly = 1) { mockView.showTicketFail(any()) }
    }

    @Test
    fun `티켓 개수가 10인 상태에서 + 버튼을 누르면 티켓의 개수는 늘어나지 않는다2`() {
        // given
        presenter = testScreenDetailPresenterFixture2()

        repeat(9) {
            presenter.plusTicket()
        }
        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showTicketFail(any()) }
    }

    @Test
    fun `reserve with date, time and ticket count2`() {
        // givne
        presenter = testScreenDetailPresenterFixture2(screenId = 1, theaterId = 1)

        // when
        presenter.reserve()

        // then
        verify(exactly = 1) { mockView.showSeatsReservation(2, any()) }
    }

    private fun testScreenDetailPresenterFixture2(
        screenId: Int = 1,
        theaterId: Int = 1,
    ): ScreenDetailPresenter {
        val movieRepository = DefaultMovieRepository(FakeMovieDataSource())
        return ScreenDetailPresenter(
            view = mockView,
            movieRepository = movieRepository,
            screenRepository = DefaultScreenRepository(FakeScreenDataSource(), movieRepository),
            reservationRepository = FakeReservationRepository(),
            screenId = screenId,
            theaterId = theaterId,
        )
    }
}
