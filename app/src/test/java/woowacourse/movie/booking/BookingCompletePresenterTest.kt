package woowacourse.movie.booking

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.booking.complete.BookingCompleteContract
import woowacourse.movie.booking.complete.BookingCompletePresenter
import woowacourse.movie.data.SettingRepository
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_A2
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEOLLEUNG
import woowacourse.movie.fixture.createTicket
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.TicketUiModel

class BookingCompletePresenterTest {
    private lateinit var presenter: BookingCompletePresenter
    private lateinit var mockView: BookingCompleteContract.View
    private lateinit var mockTicketUiData: TicketUiModel
    private val settingManager =
        object : SettingRepository {
            private var isAlarm = false

            override fun isAlarmPermitted(): Boolean = isAlarm

            override fun setAlarmPermitted(isGranted: Boolean) {
                isAlarm = isGranted
            }
        }

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)

        val seats = listOf(SEAT_A1, SEAT_A2, SEAT_C1)
        val mockTicket = createTicket(SEOLLEUNG, seats)

        mockTicketUiData = mockTicket.toUiModel()

        presenter = BookingCompletePresenter(view = mockView, settingManager)
    }

    @Test
    fun `영화 예매 정보를 화면에 표시할 수 있다`() {
        // given
        val ticketSlot = slot<TicketUiModel>()

        every { mockView.showBookingCompleteResult(capture(ticketSlot)) } just Runs

        // when
        presenter.initializeData(mockTicketUiData)

        // then
        verify { mockView.showBookingCompleteResult(any()) }

        with(ticketSlot.captured) {
            assertThat(headCount).isEqualTo(3)
            assertThat(selectedDateText).isEqualTo("2028.10.13")
            assertThat(selectedTimeText).isEqualTo("11:00")
            assertThat(seats).isEqualTo(setOf(SEAT_A1.toUiModel(), SEAT_A2.toUiModel(), SEAT_C1.toUiModel()))
            assertThat(totalPrice).isEqualTo("35,000")
        }
    }
}
