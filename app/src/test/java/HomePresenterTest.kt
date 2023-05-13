import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.Ad
import woowacourse.movie.fragment.home.HomeContract
import woowacourse.movie.fragment.home.HomePresenter
import woowacourse.movie.movie.Movie
import woowacourse.movie.movie.MovieMockData

class HomePresenterTest {

    private lateinit var mockView: HomeContract.View
    private lateinit var presenter: HomePresenter

    @Before
    fun setUp() {
        mockView = mockk(relaxed = true)
        presenter = HomePresenter(mockView)
    }

    @Test
    fun `영화_목록_작성에_필요한_데이터를_넘겨준다`() {
        // given
        val slotMovieData = slot<List<Movie>>()
        val slotAd = slot<Ad>()
        every {
            mockView.setMovieList(
                capture(slotMovieData),
                capture(slotAd),
                mockView.showTheaterPicker(),
                mockView.startAdDetailPage()
            )
        } just runs

        // when
        presenter.initMovieList()

        // then
        verify(exactly = 1) { mockView.showTheaterPicker() }
        verify(exactly = 1) { mockView.startAdDetailPage() }
        assertEquals(MovieMockData.movies10000, slotMovieData.captured)
        assertEquals(Ad.dummyAd, slotAd.captured)
    }
}
