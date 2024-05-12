package woowacourse.movie.feature.finished

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.TestFixture.makeMockSeats
import woowacourse.movie.db.ticket.Ticket
import woowacourse.movie.db.ticket.TicketDao
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
class ReservationFinishedPresenterTest {
    @MockK
    private lateinit var view: ReservationFinishedContract.View
    private lateinit var presenter: ReservationFinishedContract.Presenter
    private val mockTicketDao = mockk<TicketDao>()

    private val mockTicket =
        Ticket(
            LocalDateTime.of(LocalDate.now(), LocalTime.now()),
            movieTitle = "해리 포터와 마법사의 돌",
            theaterName = "선릉 극장",
            seats = makeMockSeats(),
            uid = 1,
        )

    @BeforeEach
    fun setUp() {
        presenter = ReservationFinishedPresenter(view, 1, mockTicketDao)
    }

    // TODO 테스트 실패 해결하기
    @Test
    fun `예매 내역을 불러온다`() {
        every { view.showReservationHistory(any()) } just runs
        presenter.loadTicket()
        verify { view.showReservationHistory(mockTicket) }
    }
}
