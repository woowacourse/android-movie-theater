import com.woowacourse.domain.seat.SeatGroup
import com.woowacourse.domain.ticket.TicketBundle
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import org.junit.jupiter.api.Assertions.assertEquals
import woowacourse.movie.activity.seatpicker.SeatPickerContract
import woowacourse.movie.activity.seatpicker.SeatPickerPresenter
import woowacourse.movie.movie.MovieBookingInfo

class SeatPickerPresenterTest {

    private lateinit var view: SeatPickerContract.View
    private lateinit var movieBookingInfo: MovieBookingInfo
    private lateinit var presenter: SeatPickerPresenter
    private lateinit var seatGroup: SeatGroup
    private lateinit var ticketBundle: TicketBundle

    @Before
    fun setUp() {
        view = mockk()
        movieBookingInfo = MovieBookingInfo("Movie Title", "2022-01-01", "20:00", 2)
        seatGroup = mockk()
        ticketBundle = TicketBundle(movieBookingInfo.ticketCount)
        presenter = SeatPickerPresenter(view, movieBookingInfo, seatGroup, ticketBundle)
    }

    @Test
    fun `화면을_초기화_할_수_있다`() {
        // given
        val slot = slot<String>()

        // when
        presenter.initPage()

        // then
        verify(exactly = 1) { view.initSeat() }
        verify(exactly = 1) { view.setMovieTitle(capture(slot)) }
        assertEquals(slot.captured, movieBookingInfo.title)
    }

    @Test
    fun `예매 완료 버튼을 눌렀을 때 다이얼로그가 보여진다`() {
        // when
        presenter.onPickDoneButtonClicked()

        // then
        verify(exactly = 1) { view.showDialog() }
    }

    @Test
    fun `모든 좌석을 선택했다면 예매 완료 버튼 색을 변경한다`() {
        // given
        val slot = slot<Boolean>()
        every { seatGroup.canAdd(ticketBundle.count) } returns false
        every { view.setPickDoneButtonColor(true) } just Runs

        // when
        presenter.getCanAddSeat()

        // then
        verify { view.setPickDoneButtonColor(capture(slot)) }
        assertEquals(true, slot.captured)
    }

//    @Test
//    fun `선택되지 않은 좌석을 선택하면 좌석이 추가된다`() {
//        val newSeat = Seat(SeatRow(1), SeatColumn(0))
//        every { seatGroup.seats.contains(newSeat) } returns true
//        every { ticketBundle.calculateTotalPrice(any(), any()) } returns 10000
//        every { presenter.progressAddSeat(any(), any(), any()) } just runs
//        presenter.onSeatClicked(4)
//
//        verify(exactly = 1) { presenter.progressAddSeat(newSeat, 4, movieBookingInfo) }
//
//    }
}
