package woowacourse.movie.presentation.booking

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Screening
import woowacourse.movie.domain.model.scheduler.Scheduler
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.JAMSIL
import java.time.LocalDate

class BookingPresenterTest {
    private lateinit var view: BookingContract.View
    private lateinit var presenter: BookingContract.Presenter
    private lateinit var scheduler: Scheduler

    private val screening =
        Screening(
            JAMSIL,
            HARRY_POTTER,
            listOf(),
        )

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        scheduler = mockk(relaxed = true)
        presenter = BookingPresenter(view, screening, scheduler)
    }

    @Test
    fun `영화의 정보와 예매 가능 날짜, 인원 수가 화면에 출력된다`() {
        // when
        presenter.loadBooking()

        // then
        verify { view.showMovie(HARRY_POTTER) }
        verify { view.showBookableDates(any(), any()) }
        verify { view.showHeadCount(1) }
    }

    @Test
    fun `날짜를 선택하면 예약 가능한 시간들이 출력된다`() {
        // given
        val date = LocalDate.of(2025, 5, 5)

        // when
        presenter.selectScreeningDate(date)

        // then
        verify { view.showBookableTimes(any(), any()) }
    }

    @Test
    fun `증가 버튼을 누르면 예매 인원을 증가시키고 출력한다`() {
        // when
        presenter.increaseHeadCount()

        // then
        verify { view.showHeadCount(2) }
    }

    @Test
    fun `현재 인원이 2 이상일 때 감소 버튼을 누르면 예매 인원을 감소시키고 출력한다`() {
        // given
        presenter.increaseHeadCount()

        // when
        presenter.decreaseHeadCount()

        // then
        verify(exactly = 2) { view.showHeadCount(any()) }
        verify { view.showHeadCount(1) }
    }

    @Test
    fun `선택 완료 버튼을 누르면 화면을 이동한다`() {
        // when
        presenter.confirmBooking()

        // then
        verify { view.navigateToSeatSelect(any()) }
    }
}
