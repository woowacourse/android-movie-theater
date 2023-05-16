package fragment

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.fragment.bookhistory.BookHistoryContract
import woowacourse.movie.fragment.bookhistory.BookHistoryPresenter
import woowacourse.movie.model.BookingHistoryData

class BookHistoryPresenterTest {
    private lateinit var presenter: BookHistoryPresenter
    private lateinit var view: BookHistoryContract.View
    private lateinit var repository: MovieRepository

    private val bookingHistory = listOf(
        BookingHistoryData(
            "title",
            "2023.5.5 09:00",
            1,
            listOf("A1"),
            "10000",
            "theater",
        ),
    )

    @Before
    fun setUp() {
        view = mockk()
        repository = mockk(relaxed = true)
        presenter = BookHistoryPresenter(view, repository)
    }

    @Test
    fun 예매내역_리스트의_어댑터를_설정한다() {
        // given
        val slot = slot<List<BookingHistoryData>>()
        every { repository.loadData() } returns bookingHistory
        every { view.setMovieRecyclerView(capture(slot)) } just Runs

        // when
        presenter.setMovieRecyclerView()

        // then
        val actual = slot.captured
        assertEquals(bookingHistory, actual)
    }
}
