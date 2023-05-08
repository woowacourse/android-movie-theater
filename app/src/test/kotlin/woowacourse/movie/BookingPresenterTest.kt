package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.BookedMovie
import woowacourse.movie.ui.booking.BookingContract
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenterTest {

    private lateinit var bookingPresenter: BookingContract.Presenter
    private lateinit var view: BookingContract.View

    @Before
    fun setUp() {
        view = mockk()
        // given 극장은 선릉이다. (10시부터 두시간 간격으로 6개의 상영시간을 가진다)
        bookingPresenter = BookingPresenter(view = view)
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

    @Test
    fun `상영 기간이 4월 26일부터 4월 30일인 경우 date 정보를 나타내는 spinner에 그 사이 기간들을 넣는다`() {
        //given
        val slot = slot<List<LocalDate>>()

        every { view.setDates(capture(slot)) } answers { println(slot.captured) }
        ignoreTimes(view)

        //when
        bookingPresenter.initDateTimes()

        //then
        val actual = slot.captured
        val expected = listOf(
            LocalDate.of(2023, 4, 26),
            LocalDate.of(2023, 4, 27),
            LocalDate.of(2023, 4, 28),
            LocalDate.of(2023, 4, 29),
            LocalDate.of(2023, 4, 30)
        )

        assertEquals(actual, expected)
        verify { view.setDates(actual) }
    }

    @Test
    fun `선릉 극장을 선택한 경우 10시부터 두시간 간격으로 6개의 상영시간을 time 정보를 가진 spinner에 넣는다`() {
        //given 기본 극장은 선릉으로 설정되어 있다
        val slot = slot<List<LocalTime>>()

        every { view.setTimes(capture(slot)) } answers { println(slot.captured) }
        ignoreDates(view)

        //when
        bookingPresenter.initDateTimes()

        //then
        val actual = slot.captured
        val expected = listOf(
            LocalTime.of(10, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(16, 0),
            LocalTime.of(18, 0),
            LocalTime.of(20, 0)
        )

        assertEquals(actual, expected)
        verify { view.setTimes(expected) }
    }

    @Test
    fun `시간을 설정하고 티켓 개수를 정하는 것을 완료하면 view는 해당 정보를 가지고 좌석 화면으로 이동한다`() {
        //given 기본 극장은 선릉으로 설정되어 있다
        val slot = slot<BookedMovie>()
        val selectedDateTime = LocalDateTime.of(
            LocalDate.of(2022, 10, 23),
            LocalTime.of(10, 0)
        )

        every { view.selectedDateTime } returns selectedDateTime
        every { view.navigateToSeatView(capture(slot)) } just Runs

        //when
        bookingPresenter.onCompletedBookingMovie()

        //then
        val actual = slot.captured
        val expected = BookedMovie(
            bookedDateTime = selectedDateTime
        )

        assertEquals(actual, expected)
        verify { view.navigateToSeatView(expected) }
    }
}