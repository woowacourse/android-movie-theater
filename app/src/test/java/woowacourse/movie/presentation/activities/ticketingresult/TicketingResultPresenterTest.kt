package woowacourse.movie.presentation.activities.ticketingresult

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.item.Reservation

internal class TicketingResultPresenterTest {

    private lateinit var view: TicketingResultContract.View
    private lateinit var presenter: TicketingResultPresenter
    private lateinit var reservation: Reservation

    @Before
    fun setUp() {
        view = mockk()
        reservation = mockk()
        presenter = TicketingResultPresenter(view)
    }

    @Test
    fun `영화제목은 가디언즈 오브 갤럭시다`() {
        // given
        every { reservation.movieTitle } returns "가디언즈 오브 갤럭시"
        val slot = slot<Reservation>()
        every { view.setMovieInformation(capture(slot)) } just Runs

        // when
        presenter.updateMovieInformation(reservation)

        // then
        val actual = slot.captured.movieTitle
        val expected = "가디언즈 오브 갤럭시"

        assertEquals(expected, actual)
    }

    @Test
    fun `티켓 금액은 13_000원이다`() {
        // given
        every { reservation.ticketPrice.amount } returns 13_000
        val slot = slot<Reservation>()
        every { view.setMovieInformation(capture(slot)) } just Runs

        // when
        presenter.updateMovieInformation(reservation)

        // then
        val actual = slot.captured.ticketPrice.amount
        val expected = 13_000

        assertEquals(expected, actual)
    }

    @Test
    fun `좌석은 A1이다`() {
        // given
        every { reservation.seats.toString() } returns "A1"
        val slot = slot<Reservation>()
        every { view.setMovieInformation(capture(slot)) } just Runs

        // when
        presenter.updateMovieInformation(reservation)

        // then
        val actual = slot.captured.seats.toString()
        val expected = "A1"

        assertEquals(expected, actual)
    }
}
