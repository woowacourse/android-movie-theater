package woowacourse.movie.presentation.ui.detail

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.ReservationInfo
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.ui.utils.DummyData.dummyScreen
import woowacourse.movie.presentation.ui.utils.DummyData.findByScreenId

@ExtendWith(MockKExtension::class)
class DetailPresenterTest {
    @MockK
    private lateinit var view: DetailContract.View

    @MockK
    private lateinit var repository: ScreenRepository

    @InjectMockKs
    private lateinit var presenter: DetailPresenter

    @Test
    fun `DetailPresenter가 유효한 상영 id값으로 loadScreen()을 했을 때, view에게 screen, count 정보를 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs

        // when
        presenter.loadScreen(0, 0)

        // then
        verify { view.showScreen(dummyScreen) }
        verify { view.showTicket(1) }
    }

    @Test
    fun `DetailPresenter가 유효하지 않은 상영 id값으로 loadScreen()을 했을 때, view에게 back과 throwable를 전달한다`() {
        // given
        val exception = NoSuchElementException()
        every { repository.findByScreenId(any(), any()) } returns Result.failure(exception)
        every { view.showToastMessage(e = exception) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadScreen(0, 0)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `DetailPresenter가 ticket 값이 1일 때 plusTicket()을 하면, view에게 티켓 개수를 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs
        presenter.loadScreen(0, 0)

        // when
        presenter.plusTicket()

        // then
        verify { view.showTicket(2) }
    }

    @Test
    fun `DetailPresenter가 ticket 값이 티켓의 최대 개수일 때 plusTicket()을 하면, view에게 snackbar message(TicketMaxCountMessage)를 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs
        every { view.showSnackBar(MessageType.TicketMaxCountMessage(Ticket.MAX_TICKET_COUNT)) } just runs
        presenter.loadScreen(0, 0)

        // when
        repeat(Ticket.MAX_TICKET_COUNT - 1) {
            presenter.plusTicket()
        }
        presenter.plusTicket()

        // then
        verify { view.showSnackBar(MessageType.TicketMaxCountMessage(Ticket.MAX_TICKET_COUNT)) }
    }

    @Test
    fun `DetailPresenter가 ticket 값이 1일 때 minusTicket()을 하면, view에게 snackbar message(TicketMinCountMessage)를 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs
        every { view.showSnackBar(MessageType.TicketMinCountMessage(Ticket.MIN_TICKET_COUNT)) } just runs
        presenter.loadScreen(0, 0)

        // when
        presenter.minusTicket()

        // then
        verify { view.showSnackBar(MessageType.TicketMinCountMessage(Ticket.MIN_TICKET_COUNT)) }
    }

    @Test
    fun `DetailPresenter가 ticket 값이 티켓의 최대 개수일 때 minusTicket()을 하면, view에게 티켓 개수를 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs
        presenter.loadScreen(0, 0)

        // when
        repeat(Ticket.MAX_TICKET_COUNT - 1) {
            every { view.showTicket(it + 2) } just runs
            presenter.plusTicket()
        }
        presenter.minusTicket()

        // then
        verify(exactly = 2) { view.showTicket(Ticket.MAX_TICKET_COUNT - 1) }
    }

    @Test
    fun `DetailPresenter가 selectSeat()를 누르면, view에게 좌석 선택 페이지로 이동하라고 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs
        every { view.navigateToSeatSelection(any()) } just runs
        presenter.loadScreen(0, 0)

        // when
        presenter.selectSeat()

        // then
        val reservationInfo =
            ReservationInfo(
                theaterId = 0,
                movieId = dummyScreen.movie.id,
                dateTime =
                    dummyScreen.selectableDates.first()
                        .getLocalDateTime(
                            dummyScreen.selectableDates.first().getSelectableTimes().first(),
                        ),
                ticketCount = 1,
            )
        verify { view.navigateToSeatSelection(reservationInfo) }
    }

    @Test
    fun `DetailPresenter가 티켓 초기값을 설정하고 좌석 선택을 요청하면, view에게 티켓 개수를 설정하고 좌석 선택 페이지로 이동하라고 전달한다`() {
        // given
        every { repository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { view.showScreen(any()) } just runs
        every { view.showTicket(any()) } just runs
        presenter.loadScreen(1, 1)

        // when
        presenter.updateTicket(5)

        // then
        verify { view.showTicket(5) }
    }
}
