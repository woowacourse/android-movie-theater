package woowacourse.movie.presentation.booking

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.movie.MovieData
import woowacourse.movie.presentation.model.CinemaModel
import woowacourse.movie.presentation.model.ReservationModel
import java.time.LocalDateTime
import java.time.LocalTime

class BookingPresenterTest {
    private lateinit var view: BookingContract.View
    private lateinit var presenter: BookingContract.Presenter
    private lateinit var movieData: MovieData

    @Before
    fun `setUp`() {
        view = mockk(relaxed = true)
        movieData = mockk(relaxed = true)
        presenter = BookingPresenter(movieData, view)
    }

    @Test
    fun `티켓의 초기값을 3으로 세팅할 수 있다`() {
        // given
        val ticketCountSlot = slot<Int>()
        every { view.setTicketCount(capture(ticketCountSlot)) } just runs

        // when
        presenter.setTicketCount(3)

        // then
        val excepted = 3
        val actual = ticketCountSlot.captured
        assertEquals(excepted, actual)
        verify { view.setTicketCount(excepted) }
    }

    @Test
    fun `초기값 1에서 티켓 수를 증가시키면 2가 된다`() {
        // given
        val ticketCountSlot = slot<Int>()
        every { view.setTicketCount(capture(ticketCountSlot)) } just runs

        // when
        presenter.addTicket()

        // then
        val expected = 2
        val actual = ticketCountSlot.captured
        assertEquals(expected, actual)
        verify { view.setTicketCount(expected) }
    }

    @Test
    fun `초기값 2에서 티켓 수를 감소시키면 1이 된다`() {
        // given
        val ticketCountSlot = slot<Int>()
        every { view.setTicketCount(capture(ticketCountSlot)) } just runs

        // when
        presenter.subTicket()

        // then
        val expected = 1
        val actual = ticketCountSlot.captured
        assertEquals(expected, actual)
        verify { view.setTicketCount(expected) }
    }

    @Test
    fun `예약을 생성한다`() {
        // given
        val reservationSlot = slot<ReservationModel>()
        val cinemaModel = CinemaModel(
            "선릉",
            1L,
            listOf(LocalTime.of(9, 0), LocalTime.of(10, 0), LocalTime.of(11, 0)),
        )
        every { presenter.setTicketCount(2) } just runs
        every { view.reservationMovie(capture(reservationSlot)) } just runs

        // when
        presenter.reserveMovie(cinemaModel, LocalDateTime.of(2023, 5, 6, 9, 0))

        // then
        val expected = ReservationModel(
            1L,
            "선릉",
            LocalDateTime.of(2023, 5, 6, 9, 0),
            2,
        )
        val actual = reservationSlot.captured
        assertEquals(expected, actual)
        verify { view.reservationMovie(expected) }
    }
}
