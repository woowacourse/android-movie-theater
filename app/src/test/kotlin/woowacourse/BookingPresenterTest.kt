package woowacourse

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.app.presentation.ui.booking.BookingContract
import woowacourse.app.presentation.ui.booking.BookingPresenter
import woowacourse.domain.movie.Movie
import woowacourse.domain.movie.ScreeningDate
import woowacourse.domain.movie.ScreeningPeriod
import java.time.LocalDate
import java.time.LocalTime

class BookingPresenterTest {
    private lateinit var presenter: BookingContract.Presenter
    private lateinit var view: BookingContract.View
    private lateinit var movie: Movie

    @Before
    fun setUp() {
        view = mockk()
        movie = Movie(
            1,
            "해리 포터",
            ScreeningPeriod(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 4, 10)),
            120,
            "재밌음",
        )
        presenter = BookingPresenter(view, movie)
        presenter.restoreTicketCount(INIT_TICKET_COUNT)
    }

    @Test
    fun `티켓 수를 반환한다`() {
        // given
        // when
        val actual = presenter.getTicketCount()

        // then
        assertEquals(INIT_TICKET_COUNT, actual)
    }

    @Test
    fun `티켓 수를 저장한다`() {
        // given
        // when
        presenter.restoreTicketCount(3)
        val actual = presenter.getTicketCount()

        // then
        assertEquals(3, actual)
    }

    @Test
    fun `초기 티켓 수를 세팅한다`() {
        // given
        val slot = slot<Int>()
        every { view.showTicketCount(capture(slot)) } answers { println("slot: ${slot.captured}") }

        // when
        presenter.initTicketCount()
        val actual = slot.captured

        // then
        assertEquals(INIT_TICKET_COUNT, actual)
        verify(exactly = 1) { view.showTicketCount(INIT_TICKET_COUNT) }
    }

    @Test
    fun `더하기를 누르면 티켓 수가 올라간다`() {
        // given
        val slot = slot<Int>()
        every { view.showTicketCount(capture(slot)) } answers { println("slot: ${slot.captured}") }

        // when
        presenter.addTicket()
        val actual = slot.captured

        // then
        assertEquals(INIT_TICKET_COUNT + 1, actual)
        verify(exactly = 1) { view.showTicketCount(INIT_TICKET_COUNT + 1) }
    }

    @Test
    fun `빼기를 누르면 티켓 수가 감소한다`() {
        // given : 초기 값인 1에선 빼도 1이므로 2를 만들어주고 테스트를 시작한다.
        val slot = slot<Int>()
        every { view.showTicketCount(capture(slot)) } answers { println("slot: ${slot.captured}") }

        // when
        presenter.subTicket()
        val actual = slot.captured

        // then
        assertEquals(INIT_TICKET_COUNT - 1, actual)
    }

    @Test
    fun `날짜에 해당하는 시간대를 가져온다`() {
        // given
        val date = LocalDate.of(2024, 3, 1)
        val expected = ScreeningDate(date).screeningTimes

        // when
        val actual = presenter.getScreeningTimes(date)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `스피너의 날짜들과 시간들을 넣는다`() {
        // given
        val datesSlot = slot<List<LocalDate>>()
        val timesSlot = slot<List<LocalTime>>()
        every {
            view.initDateTime(
                capture(datesSlot),
                capture(timesSlot),
            )
        } answers { println("dates:${datesSlot.captured}, times:${timesSlot.captured}") }

        // when
        presenter.initDateTimes()
        val actualDates = datesSlot.captured
        val actualTimes = timesSlot.captured

        // then
        assertEquals(movie.screeningDates, actualDates)
        assertEquals(presenter.getScreeningTimes(movie.startDate), actualTimes)
        verify(exactly = 1) { view.initDateTime(actualDates, actualTimes) }
    }

    @Test
    fun `예약을 완료한다`() {
        // given
        every { view.startSeatActivity(INIT_TICKET_COUNT) } returns Unit
        // when
        presenter.completeBooking()

        // then
        verify(exactly = 1) { view.startSeatActivity(INIT_TICKET_COUNT) }
    }

    companion object {
        private const val INIT_TICKET_COUNT = 2
    }
}
