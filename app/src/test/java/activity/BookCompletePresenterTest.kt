package activity

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.activity.bookComplete.BookCompleteContract
import woowacourse.movie.activity.bookComplete.BookCompletePresenter
import woowacourse.movie.model.BookingHistoryData
import woowacourse.movie.model.TicketData

class BookCompletePresenterTest {
    private lateinit var presenter: BookCompletePresenter
    private lateinit var view: BookCompleteContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        val ticketData =
            BookingHistoryData("title", "2023.5.5 09:00", 1, listOf("A1"), "100000", "theater")
        presenter = BookCompletePresenter(view, ticketData)
    }

    @Test
    fun 화면을_초기화한다() {
        // given
        val slot = slot<TicketData>()
        every { view.initView(capture(slot)) } answers { nothing }

        // when
        presenter.initView()

        // then
        val actual = slot.captured
        assertEquals(presenter.data, actual)
    }
}
