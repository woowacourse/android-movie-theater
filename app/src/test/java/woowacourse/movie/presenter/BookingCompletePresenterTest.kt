package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.setting.SettingRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.seat.Col
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.ticket.Ticket
import woowacourse.movie.view.home.complete.BookingCompleteContract
import woowacourse.movie.view.home.complete.BookingCompletePresenter
import java.time.LocalDate
import java.time.LocalTime

class BookingCompletePresenterTest {
    private lateinit var view: BookingCompleteContract.View
    private lateinit var presenter: BookingCompletePresenter
    private lateinit var repository: TicketRepository
    private lateinit var manager: SettingRepository
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

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        manager = mockk()
        repository = mockk()
        presenter = BookingCompletePresenter(view, repository, manager, ticket)
    }

    @Test
    fun `예매 정보를 표시한다`() {
        // when
        presenter.loadTicket()

        // then
        verify { view.showTicket(ticket) }
    }

    @Test
    fun `알림 설정이 꺼져있으면 알림을 맞추지 않는다`() {
        // given
        every { manager.isNotificationEnabled() } returns false

        // when
        presenter.decideNotification(ticket)

        // then
        verify(exactly = 0) { view.setNotification(any(), any()) }
    }

    @Test
    fun `알림 설정이 켜져있고 알림 권한이 있으면 안내 문구를 출력하지 않고 알림을 설정한다`() {
        // given
        every { manager.isNotificationEnabled() } returns true
        every { view.isNotificationPermitted() } returns true

        // when
        presenter.decideNotification(ticket)

        // then
        verify(exactly = 0) { view.notifyNoNotificationPermission() }
        verify { view.setNotification(ticket, any()) }
    }

    @Test
    fun `알림 설정이 켜져있지만 알림 권한이 없으면 알림을 설정하지 않고 안내 문구를 출력한다`() {
        // given
        every { manager.isNotificationEnabled() } returns true
        every { view.isNotificationPermitted() } returns false

        // when
        presenter.decideNotification(ticket)

        // then
        verify { view.notifyNoNotificationPermission() }
        verify(exactly = 0) { view.setNotification(ticket, any()) }
    }
}
