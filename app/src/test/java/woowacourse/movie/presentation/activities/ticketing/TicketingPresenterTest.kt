package woowacourse.movie.presentation.activities.ticketing

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.item.Movie
import java.time.LocalDate

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
        every { movie.startDate } returns LocalDate.of(2023, 5, 3)
        every { movie.endDate } returns LocalDate.of(2023, 5, 4)
        every { movie.runningTime } returns 143
        every { movie.introduce } returns "vol 3"

        presenter = TicketingPresenter(view, movie)

        val slot = slot<Movie>()
        every { view.setMovieData(capture(slot)) } just Runs

        // when
        presenter.showMovieIntroduce()

        // then
        val expected = "가디언즈 오브 갤럭시"
        val actual = slot.captured.title
        assertEquals(expected, actual)
    }

    @Test
    fun `평일인 5월 4일의 영화 시간표 중 첫 번째는 10시다`() {
        // given
        val movieDate = MovieDate(2023, 5, 4)
        val slot = slot<List<MovieTime>>()
        every { view.setMovieTimes(capture(slot)) } just Runs

        // when
        presenter.updateMovieTimes(movieDate)

        // then
        val actual = slot.captured[0]
        val expected = MovieTime(10, 0)
        assertEquals(expected, actual)
    }

    @Test
    fun `주말인 5월 6일의 영화 시간표 중 첫 번째는 9시다`() {
        // given
        val movieDate = MovieDate(2023, 5, 7)
        val slot = slot<List<MovieTime>>()
        every { view.setMovieTimes(capture(slot)) } just Runs

        // when
        presenter.updateMovieTimes(movieDate)

        // then
        val actual = slot.captured[0]
        val expected = MovieTime(9, 0)
        assertEquals(expected, actual)
    }
}
