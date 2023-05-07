package fragment

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.fragment.bookhistory.BookHistoryContract
import woowacourse.movie.fragment.bookhistory.BookHistoryPresenter
import woowacourse.movie.fragment.bookhistory.adapter.BookHistoryRecyclerViewAdapter
import woowacourse.movie.model.BookingHistoryData

class BookHistoryPresenterTest {
    private lateinit var presenter: BookHistoryPresenter
    private lateinit var view: BookHistoryContract.View

    private val bookingHistory = listOf(
        BookingHistoryData(
            "title",
            "2023.5.5 09:00",
            1,
            listOf("A1"),
            "10000",
            "theater"
        )
    )

    @Before
    fun setUp() {
        view = mockk()
        presenter = BookHistoryPresenter(view)
    }

    @Test
    fun 예매내역_리스트의_어댑터를_설정한다() {
        // given
        val slot = slot<BookHistoryRecyclerViewAdapter>()
        every { view.setMovieRecyclerView(capture(slot)) } answers { nothing }

        // when
        presenter.setMovieRecyclerView(bookingHistory) { }

        // then
        val actual = slot.captured
        val expect = BookHistoryRecyclerViewAdapter(
            bookingHistory
        ) {}
        assertEquals(expect.itemCount, actual.itemCount)
    }
}
