package woowacourse.movie.movie.ticket

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.movie.dto.movie.MovieDateDto
import woowacourse.movie.movie.dto.movie.MovieTimeDto
import woowacourse.movie.movie.dto.ticket.TicketCountDto
import java.time.LocalDate
import java.time.LocalTime

internal class TicketPresenterTest{

    private lateinit var view: TicketContract.View
    private lateinit var presenter: TicketContract.Presenter

    private val fakeData = BookingMovieEntity(
        title = "Joker",
        date = MovieDateDto(LocalDate.of(2022, 5, 15)),
        time = MovieTimeDto(LocalTime.of(14, 30)),
        theaterName = "Megabox",
        price = 10000,
        seats = "A1, A2",
        ticketCount = TicketCountDto(2)
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = TicketPresenter(view)
    }

    @Test
    fun `예매한 영화 가격이 얼마인지를 잘 보여진다`() {
        val moviePriceSlot = slot<String>()

        every { view.formatTicketPrice(any()) } returns "10000"
        every { view.showTicketPrice(capture(moviePriceSlot)) } answers { nothing }

        presenter.initActivity(fakeData)

        val expected = 10000.toString()
        assertEquals(expected, moviePriceSlot.captured)
        verify { view.showTicketPrice(moviePriceSlot.captured) }
    }

    @Test
    fun `예매한 티켓 좌석 정보를 잘 보여진다`() {
        val movieSeatSlot = slot<String>()

        every { view.formatTicketSeat(any(), any(), any()) } returns "2명 / A1, A2 / Megabox"
        every { view.showTicketInfo(capture(movieSeatSlot)) } answers { nothing }

        presenter.initActivity(fakeData)

        val expected = "2명 / A1, A2 / Megabox"
        assertEquals(expected, movieSeatSlot.captured)
        verify { view.showTicketInfo(movieSeatSlot.captured) }
    }

    @Test
    fun `예매한 영화의 영화 이름을 잘 보여진다`() {
        val movieTitleSlot = slot<String>()

        every { view.showMovieTitle(capture(movieTitleSlot)) } answers { nothing }

        presenter.initActivity(fakeData)

        val expected = "Joker"
        assertEquals(expected, movieTitleSlot.captured)
        verify { view.showMovieTitle(movieTitleSlot.captured) }
    }

    @Test
    fun `예매한 영화의 시간과 날짜가 잘 보여진다`() {
        val movieDateSlot = slot<String>()

        every { view.formatTicketDateTime(any(), any()) } returns "2022-05-15 14:30"
        every { view.showMovieDate(capture(movieDateSlot)) } answers { nothing }

        presenter.initActivity(fakeData)

        val expected = "2022-05-15 14:30"
        assertEquals(expected, movieDateSlot.captured)
        verify { view.showMovieDate(movieDateSlot.captured) }
    }
}