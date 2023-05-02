package woowacourse.movie

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ui.booking.BookingContract

class BookingPresenterTest {

    private lateinit var bookingPresenter: BookingContract.Presenter
    private lateinit var view: BookingContract.View

    @Before
    fun setUp() {
        view = mockk()
        bookingPresenter = BookingPresenter(view)
    }

    @Test
    fun `티켓 카운트를 초기화하면 티켓 개수를 나타내는 텍스트 뷰를 1로 세팅한다`() {
        // given
        val slot = slot<Int>()
        every { view.setTicketCountText(capture(slot)) } answers { println(slot.captured) }

        // when
        bookingPresenter.initTicketCount()

        // then
        val actual = slot.captured
        assertEquals(1, actual)
        verify { view.setTicketCountText(actual) }
    }

    @Test
    fun `티켓 개수를 줄이면 텍스트 뷰에 존재하는 티켓 개수의 값을 1만큼 감소시킨다`() {
        // given
        val slot = slot<Int>()
        every { view.setTicketCountText(capture(slot)) } answers { println(slot.captured) }
        bookingPresenter.plusTicketCount()
        bookingPresenter.plusTicketCount()

        // when
        bookingPresenter.minusTicketCount()

        // then
        val actual = slot.captured
        assertEquals(2, actual)
        verify { view.setTicketCountText(actual) }
    }

    @Test
    fun `티켓 개수를 늘리면 텍스트 뷰에 존재하는 티켓 개수의 값을 1만큼 증가시킨다`() {
        // given
        val slot = slot<Int>()
        every { view.setTicketCountText(capture(slot)) } answers { println(slot.captured) }

        // when
        bookingPresenter.plusTicketCount()

        // then
        val actual = slot.captured
        assertEquals(2, actual)
        verify { view.setTicketCountText(actual) }
    }
}