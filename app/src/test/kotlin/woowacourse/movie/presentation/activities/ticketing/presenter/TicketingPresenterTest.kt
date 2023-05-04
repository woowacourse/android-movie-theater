package woowacourse.movie.presentation.activities.ticketing.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.activities.ticketing.contract.TicketingContract
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketingState

internal class TicketingPresenterTest {
    private lateinit var presenter: TicketingContract.Presenter
    private lateinit var view: TicketingContract.View

    @Before
    internal fun setUp() {
        presenter = TicketingPresenter(mockk(relaxed = true))
        view = mockk(relaxed = true)
        presenter.attach(view)
    }

    @Test
    internal fun 프레젠터에_View를_Attach할_때_View_초기화가_이루어진다() {
        // given
        /* ... */

        // when
        presenter.attach(view)

        // then
        verify(atLeast = 1) { view.initView(any(), any()) }
    }

    @Test
    internal fun 티켓_상태를_변경하면_변경된_상태를_뷰에_반영한다() {
        // given
        val ticketingState = mockk<TicketingState>(relaxed = true)
        every { ticketingState.movieDate } returns MovieDate(2023, 1, 1)

        // when
        presenter.setState(ticketingState)

        // then
        verify(atLeast = 1) { view.showTicketingState(any(), any(), any()) }
    }

    @Test
    internal fun 티켓_수량을_증가시키면_증가된_수량을_뷰에_반영한다() {
        // given
        val originTicketCount = 2

        val ticketingState = TicketingState(
            movie = mockk(),
            ticket = Ticket(originTicketCount),
            movieDate = MovieDate(2023, 1, 1),
        )
        presenter.setState(ticketingState)

        // when
        presenter.plusCount()

        // then
        verify(atLeast = 1) { view.updateCount(originTicketCount + 1) }
    }

    @Test
    internal fun 티켓_수량을_감소시키면_감소된_수량을_뷰에_반영한다() {
        // given
        val originTicketCount = 2

        val ticketingState = TicketingState(
            movie = mockk(),
            ticket = Ticket(originTicketCount),
            movieDate = MovieDate(2023, 1, 1),
        )
        presenter.setState(ticketingState)

        // when
        presenter.minusCount()

        // then
        verify(atLeast = 1) { view.updateCount(originTicketCount - 1) }
    }

    @Test
    internal fun 날짜와_시간이_선택되지_않았다면_경고_메시지를_보여준다() {
        // given
        val ticketingState = TicketingState(
            movie = mockk(),
            ticket = Ticket(),
            movieDate = null,
            movieTime = null,
        )
        presenter.setState(ticketingState)

        // when
        presenter.onClickTicketingButton()

        // then
        verify(exactly = 1) { view.showUnSelectDateTimeAlertMessage() }
    }

    @Test
    internal fun 날짜와_시간이_선택되어_있다면_자리_선택_화면을_보여준다() {
        // given
        val ticketingState = TicketingState(
            movie = mockk(),
            ticket = Ticket(),
            movieDate = MovieDate(2023, 1, 1),
            movieTime = mockk(),
        )
        presenter.setState(ticketingState)

        // when
        presenter.onClickTicketingButton()

        // then
        verify(exactly = 1) { view.showSeatPickerScreen(any()) }
    }
}
