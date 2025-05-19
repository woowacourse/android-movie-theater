package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.seat.Col
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.history.BookingHistoryContract
import woowacourse.movie.view.history.BookingHistoryPresenter
import java.time.LocalDate
import java.time.LocalTime

class BookingHistoryPresenterTest {
    private lateinit var view: BookingHistoryContract.View
    private lateinit var presenter: BookingHistoryPresenter
    private lateinit var repository: TicketRepository

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        repository = mockk()
        presenter = BookingHistoryPresenter(view, repository)
    }

    @Test
    fun `예매 내역이 화면에 표시된다`() {
        // given
        val tickets =
            listOf(
                Ticket(
                    "해리 포터와 마법사의 돌",
                    "선릉 극장",
                    LocalDate.of(2025, 5, 11),
                    LocalTime.of(12, 0),
                    AdmissionCount(1),
                    setOf(Seat(Col(0), Row(0))),
                    12000,
                ),
            )
        every { repository.getAll() } returns tickets

        // when
        presenter.loadTickets()

        // then
        verify { view.showTickets(tickets) }
    }

    @Test
    fun `예매 내역을 선택하면 예약 완료 화면으로 이동한다`() {
        // given
        val ticket =
            Ticket(
                "해리 포터와 마법사의 돌",
                "선릉 극장",
                LocalDate.of(2025, 5, 11),
                LocalTime.of(12, 0),
                AdmissionCount(1),
                setOf(Seat(Col(0), Row(0))),
                12000,
            )

        // when
        presenter.selectHistory(ticket)

        // then
        verify { view.moveToBookingComplete(ticket) }
    }
}
