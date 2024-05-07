package woowacourse.movie.presenter.history

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.MockReservationTicketRepository
import woowacourse.TestFixture.makeMockReservationTicket
import woowacourse.movie.model.ticket.ReservationTicket

@ExtendWith(MockKExtension::class)
class ReservationHistoryPresenterTest {
    @MockK
    private lateinit var view: ReservationHistoryContract.View
    private lateinit var presenter: ReservationHistoryContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = ReservationHistoryPresenter(view, MockReservationTicketRepository())
    }

    @Test
    fun `예약_목록의_하나의_아이템을_누르면_선택된_티켓의_ID를_가지고_예매_상세화면으로_이동한다`() {
        val expectedTicketId = 0L
        val reservationTicket = makeMockReservationTicket()
        every {
            view.navigateToDetail(
                reservationTicket
            )
        } answers {
            val actualTicket = arg<ReservationTicket>(0)
            Assertions.assertEquals(actualTicket.ticketId, expectedTicketId)
        }
        presenter.loadReservationTicket(reservationTicket)
        verify { view.navigateToDetail(reservationTicket) }
    }

}
