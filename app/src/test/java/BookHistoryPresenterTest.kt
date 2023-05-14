import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.database.BookHistories
import woowacourse.movie.database.BookingHistoryRepository
import woowacourse.movie.fragment.bookhistory.BookHistoryContract
import woowacourse.movie.fragment.bookhistory.BookHistoryPresenter
import woowacourse.movie.movie.MovieBookingSeatInfo

class BookHistoryPresenterTest {

    private lateinit var view: BookHistoryContract.View
    private lateinit var presenter: BookHistoryPresenter

    @Before
    fun setUp() {
        view = mockk()
        presenter = BookHistoryPresenter(view)
    }

    @Test
    fun `영화 예매 목록의 아이템을 클릭하면 예매 내역 화면으로 이동한다`() {
        // given
        every { view.showDetailPage(any()) } just runs

        // when
        presenter.onClickItem(0)

        // then
        verify { view.showDetailPage(0) }
    }

    @Test
    fun 예매_정보를_가져올_수_있다() {
        // given
        val bookingHistory: BookingHistoryRepository = mockk()
        every { bookingHistory.isEmpty() } returns false
        every { bookingHistory.getAll() } returns listOf(MovieBookingSeatInfo.dummyData)

        // when
        presenter.reloadBookingData(bookingHistory)

        // then
        verify(exactly = 1) { BookHistories.items.clear() }
        verify(exactly = 1) { BookHistories.items.addAll(bookingHistory.getAll()) }
    }
}
