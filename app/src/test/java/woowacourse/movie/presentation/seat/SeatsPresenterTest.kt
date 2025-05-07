package woowacourse.movie.presentation.seat

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.fixture.A1
import woowacourse.movie.fixture.TICKET

class SeatsPresenterTest {
    private lateinit var view: SeatSelectContract.View
    private lateinit var presenter: SeatSelectContract.Presenter
    private lateinit var ticket: Ticket

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        ticket = TICKET
        presenter = SeatsPresenter(view, ticket)
    }

    @Test
    fun `좌석, 영화 제목, 금액이 출력된다`() {
        // when
        presenter.loadSeatSelect()

        // then
        verify { view.showMovieInfo(ticket.movie) }
        verify { view.showTotalPrice(ticket.totalPrice()) }
    }

    @Test
    fun `좌석을 선택하고 다시 선택하면 좌석 선택을 해제한다`() {
        // given
        val seat = A1

        // when
        presenter.selectSeat(seat) // 첫 번째 클릭 - 선택

        // then
        verify { view.updateSeatSelectionState(seat, true) }
        verify { view.updateConfirmButtonState(any()) }
        verify { view.showTotalPrice(any()) }

        // when
        presenter.selectSeat(seat) // 두 번째 클릭 - 해제

        // then
        verify { view.updateSeatSelectionState(seat, false) }
        verify { view.updateConfirmButtonState(any()) }
        verify { view.showTotalPrice(any()) }
    }

    @Test
    fun `예매 완료 버튼을 클릭하면 예매 완료 화면으로 이동한다`() {
        // when
        presenter.finishBooking()

        // then
        verify { view.navigateToSummary(any()) }
    }
}
