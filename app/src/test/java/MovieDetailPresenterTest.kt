import com.woowacourse.domain.Counter
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.moviedetail.MovieDetailContract
import woowacourse.movie.activity.moviedetail.MovieDetailPresenter
import woowacourse.movie.movie.Movie

class MovieDetailPresenterTest {
    private lateinit var view: MovieDetailContract.View
    private var counter: Counter = Counter(MIN_COUNT)
    private lateinit var movieData: Movie
    private lateinit var presenter: MovieDetailPresenter

    @Before
    fun setUp() {
        view = mockk()
        counter = Counter(MIN_COUNT)
        movieData = Movie.dummyData
        presenter = MovieDetailPresenter(view, counter, movieData)
    }

    @Test
    fun `티켓개수가 최소 수와 같다면 true를 반환한다`() {
        // given

        // when
        val actual = presenter.isMinTicketCount()

        // then
        assertEquals(true, actual)
    }

    @Test
    fun `티켓 개수를 반환해준다`() {
        // given
        every { view.setTicketCountText(any()) } just runs
        presenter.addTicket()

        // when
        val actual = presenter.getTicketCount()

        // then
        assertEquals(2, actual)
    }

    @Test
    fun `티켓을 하나 증가시키면 뷰의 티켓 개수가 증가한다`() {
        // given
        every { view.setTicketCountText(any()) } just runs
        val slot = slot<Int>()

        // when
        presenter.addTicket()

        // then
        verify { view.setTicketCountText(capture(slot)) }
        assertEquals(slot.captured, 2)
    }

    @Test
    fun `티켓을 하나 감소시키면 뷰의 티켓 개수가 감소한다`() {
        // given
        every { view.setTicketCountText(any()) } just runs
        val slot = slot<Int>()

        // when
        presenter.subTicket()

        // then
        verify { view.setTicketCountText(capture(slot)) }
        assertEquals(slot.captured, 1)
    }

    companion object {
        private const val MIN_COUNT = 1
    }
}
