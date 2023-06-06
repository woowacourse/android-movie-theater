package woowacourse.movie.presentation.activities.ticketing

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.item.Movie

internal class TicketingPresenterTest {

    private lateinit var presenter: TicketingPresenter
    private lateinit var view: TicketingContract.View
    private lateinit var movie: Movie

    @Before
    fun setUp() {
        view = mockk()
        movie = mockk()
        presenter = TicketingPresenter(view, movie)
    }

    @Test
    fun `플러스 버튼을 클릭하면 티켓의 매수가 1 올라간다`() {
        // given
        val slot = slot<Ticket>()
        every { view.setTicketCountText(capture(slot)) } just Runs
        val expected = 2

        // when
        presenter.addTicketCount()

        // then
        val actual = slot.captured.count
        assertEquals(expected, actual)
    }

    @Test
    fun `티켓의 매수가 1인 상태에서 마이너스 버튼을 클릭하면 티켓의 매수가 1이다`() {
        // given
        val slot = slot<Ticket>()
        every { view.setTicketCountText(capture(slot)) } just Runs
        val expected = 1

        // when
        presenter.subTicketCount()

        // then
        val actual = slot.captured.count
        assertEquals(expected, actual)
    }

    @Test
    fun `티켓의 매수가 2인 상태에서 마이너스 버튼을 클릭하면 티켓의 매수가 1이다`() {
        // given
        val slot = slot<Ticket>()
        every { view.setTicketCountText(capture(slot)) } just Runs
        presenter.addTicketCount()
        val expected = 1

        // when
        presenter.subTicketCount()

        // then
        val actual = slot.captured.count
        assertEquals(expected, actual)
    }

    @Test
    fun `영화의 이름은 가디언즈 오브 갤럭시다`() {
        // given
        every { movie.title } returns "가디언즈 오브 갤럭시"

        presenter = TicketingPresenter(view, movie)

        val slot = slot<Movie>()
        every { view.setMovieDetails(capture(slot)) } just Runs

        // when
        presenter.updateMovieDetails()

        // then
        val expected = "가디언즈 오브 갤럭시"
        val actual = slot.captured.title
        assertEquals(expected, actual)
    }

    @Test
    fun `액티비티가 열리면 넘어온 시간표가 나온다`() {
        // given
        every { view.setMovieTimes() } just Runs

        // when
        presenter.updateMovieTimes()

        // then
        verify(exactly = 1) { view.setMovieTimes() }
    }
}
