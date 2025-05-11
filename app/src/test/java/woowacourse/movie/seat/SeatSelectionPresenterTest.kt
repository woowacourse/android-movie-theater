package woowacourse.movie.seat

import android.content.Context
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.SettingRepository
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toDomain
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.SeatUiModel
import woowacourse.movie.ui.model.TicketUiModel
import java.time.LocalDateTime
import java.time.ZoneId

class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionPresenter
    private lateinit var mockView: SeatSelectionContract.View
    private lateinit var mockTicketUiData: TicketUiModel
    private lateinit var mockContext: Context
    private val settingManager =
        object : SettingRepository {
            private var isAlarm = true

            override fun isAlarmPermitted(): Boolean = isAlarm

            override fun setAlarmPermitted(isGranted: Boolean) {
                isAlarm = isGranted
            }
        }

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        mockTicketUiData = createTicket(SEOLLEUNG, listOf(), 2).toUiModel()

        presenter = SeatSelectionPresenter(mockView, settingManager)
        presenter.initializeData(mockTicketUiData)
    }

    @Test
    fun `좌석을_클릭하면_좌석_상태와_버튼_활성화_상태를_갱신한다`() {
        // given
        val seatSlot = slot<SeatUiModel>()
        val isSelectedSlot = slot<Boolean>()
        val canBookSlot = slot<Boolean>()
        val ticketSlot = slot<TicketUiModel>()

        every { mockView.showSeatState(capture(seatSlot), capture(isSelectedSlot)) } just Runs
        every { mockView.updateCanBook(capture(canBookSlot)) } just Runs
        every { mockView.showTicket(capture(ticketSlot)) } just Runs

        // when
        presenter.updateSeats(0, 0)

        // then
        verify {
            mockView.showSeatState(SEAT_A1.toUiModel(), true)
            mockView.updateCanBook(any())
            mockView.showTicket(any())
        }

        assertThat(seatSlot.captured).isEqualTo(SEAT_A1.toUiModel())
        assertThat(isSelectedSlot.captured).isTrue()
        assertThat(canBookSlot.captured).isFalse()
        assertThat(ticketSlot.captured.seats).isEqualTo(setOf(SEAT_A1.toUiModel()))
        assertThat(ticketSlot.captured.totalPrice).isEqualTo("10,000")
    }

    @Test
    fun `예약버튼_클릭시_예약_다이얼로그를_표시한다`() {
        // given
        val ticket = slot<TicketUiModel>()

        every { mockView.showBookingAlertDialog(capture(ticket)) } just Runs

        // when
        presenter.completeBooking()

        // then
        verify { mockView.showBookingAlertDialog(any()) }

        assertThat(ticket.captured).isEqualTo(mockTicketUiData)
    }

    @Test
    fun `알림_권한이_허용된_경우_알람이_설정된다`() {
        // given
        mockContext = mockk(relaxed = true)
        val ticketSlot = slot<TicketUiModel>()
        val timeSlot = slot<Long>()

        every { mockView.makeAlarm(capture(ticketSlot), capture(timeSlot)) } just Runs

        // when
        presenter.completeSeatsSelection(mockContext)

        val expected =
            LocalDateTime.of(mockTicketUiData.toDomain().selectedDate, mockTicketUiData.toDomain().selectedTime)
                .minusMinutes(30)
                .atZone(ZoneId.systemDefault())
                .toInstant()
                .toEpochMilli()

        // then
        verify {
            mockView.makeAlarm(any(), any())
        }

        assertThat(ticketSlot.captured).isEqualTo(mockTicketUiData)
        assertThat(timeSlot.captured).isEqualTo(expected)
    }
}
