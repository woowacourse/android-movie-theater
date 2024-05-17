package woowacourse.movie.ui.detail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.FakeImage
import woowacourse.movie.domain.repository.FakeMovieRepository
import woowacourse.movie.domain.repository.FakeReservationRepository
import woowacourse.movie.domain.repository.FakeScreenRepository
import woowacourse.movie.ui.MovieDetailUI
import woowacourse.movie.ui.ScreenDetailUI
import java.time.LocalDate

class ScreenDetailPresenterTest {
    private lateinit var mockView: ScreenDetailContract.View
    private lateinit var presenter: ScreenDetailContract.Presenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<ScreenDetailContract.View>()
        presenter =
            ScreenDetailPresenter(
                view = mockView,
                movieRepository = FakeMovieRepository(),
                screenRepository = FakeScreenRepository(),
                reservationRepository = FakeReservationRepository(),
            )
    }

    private val fakeScreenDetailUI =
        ScreenDetailUI(
            id = 1,
            movieDetailUI =
                MovieDetailUI(
                    title = "title1",
                    runningTime = 1,
                    description = "description1",
                    image = FakeImage("1"),
                ),
            dateRange = DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
        )

    @Test
    fun `영화 정보를 표시`() {
        // given
        every { mockView.showScreen(fakeScreenDetailUI) } just runs
        every { mockView.showDateTimePicker(any(), any(), any(), any()) } just runs

        // when
        presenter.loadScreen(1)

        // then
        verify(exactly = 1) { mockView.showScreen(fakeScreenDetailUI) }
        verify(exactly = 1) { mockView.showDateTimePicker(any(), any(), any(), any()) }
    }

    @Test
    fun `첫 화면에서 티켓의 개수는 1이다`() {
        // given
        every { mockView.showTicket(1) } just runs

        // when
        presenter.loadTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(1) }
    }

    @Test
    fun `처음 화면에서 + 버튼을 누르면 티켓의 개수는 2이다`() {
        // given
        every { mockView.showTicket(2) } just runs

        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showTicket(2) }
    }

    @Test
    fun `처음 화면에서 - 버튼을 누르면 티켓의 개수는 1이다`() {
        // given
        every { mockView.showToastMessage(any()) } just runs

        // when
        presenter.minusTicket()

        // then
        verify(exactly = 1) { mockView.showToastMessage(any()) }
    }

    @Test
    fun `티켓 개수가 10인 상태에서 + 버튼을 누르면 티켓의 개수는 늘어나지 않는다`() {
        // given
        every { mockView.showTicket(any()) } just runs
        every { mockView.showToastMessage(any()) } just runs

        repeat(9) {
            presenter.plusTicket()
        }
        // when
        presenter.plusTicket()

        // then
        verify(exactly = 1) { mockView.showToastMessage(any()) }
    }

    @Test
    fun `날짜_시간_티켓_숫자를_가지고_예약한다`() {
        // given
        every { mockView.navigateToSeatsReservation(2, any()) } just runs

        // when
        presenter.reserve(1, 1)

        // then
        verify(exactly = 1) { mockView.navigateToSeatsReservation(2, any()) }
    }
}
