import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.bookcomplete.BookCompleteContract
import woowacourse.movie.activity.bookcomplete.BookCompletePresenter
import woowacourse.movie.movie.MovieBookingInfo
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookCompletePresenterTest {
    private lateinit var view: BookCompleteContract.View
    private lateinit var movieBookingSeatInfo: MovieBookingSeatInfo
    private lateinit var presenter: BookCompletePresenter

    @Before
    fun setUp() {
        view = mockk()
        movieBookingSeatInfo = MovieBookingSeatInfo.dummyData
        presenter = BookCompletePresenter(view, movieBookingSeatInfo)
    }

    @Test
    fun `영화_예매_정보가_더미데이터라면_메시지를_보여준다`() {
        // given
        every { view.showMessageIfDummyData() } just runs

        // when
        presenter.progressIfDummyData()

        // then
        verify(exactly = 1) { view.showMessageIfDummyData() }
    }

    @Test
    fun 영화_예매_정보가_더미데이터가_아니라면_메시지를_보여주지않는다() {
        // given
        movieBookingSeatInfo =
            MovieBookingSeatInfo(MovieBookingInfo.dummyData, listOf("A1", "A2"), "20000원")
        presenter = BookCompletePresenter(view, movieBookingSeatInfo)
        every { view.showMessageIfDummyData() } just runs

        // when
        presenter.progressIfDummyData()

        // then
        verify(exactly = 0) { view.showMessageIfDummyData() }
    }

    @Test
    fun `데이터를_넘겨_화면을_초기화_시킨다`() {
        // given
        every { view.initMovieTitle(movieBookingSeatInfo.movieBookingInfo.title) } just runs
        every { view.initBookDate(movieBookingSeatInfo.movieBookingInfo.formatBookingTime()) } just runs
        every {
            view.initBookPersonCount(
                movieBookingSeatInfo.movieBookingInfo.ticketCount,
                movieBookingSeatInfo.seats
            )
        } just runs
        every { view.initBookTotalPrice(movieBookingSeatInfo.totalPrice) } just runs

        // when
        presenter.initBookCompletePage()

        // then
        verify { view.initMovieTitle(movieBookingSeatInfo.movieBookingInfo.title) }
        verify { view.initBookDate(movieBookingSeatInfo.movieBookingInfo.formatBookingTime()) }
        verify {
            view.initBookPersonCount(
                movieBookingSeatInfo.movieBookingInfo.ticketCount,
                movieBookingSeatInfo.seats
            )
        }
        verify { view.initBookTotalPrice(movieBookingSeatInfo.totalPrice) }
    }
}
