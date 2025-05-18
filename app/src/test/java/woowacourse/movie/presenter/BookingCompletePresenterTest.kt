package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ui.complete.BookingCompleteContract
import woowacourse.movie.ui.complete.BookingCompletePresenter

class BookingCompletePresenterTest {
    private lateinit var view: BookingCompleteContract.View
    private lateinit var presenter: BookingCompletePresenter

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingCompletePresenter(view)
        presenter.loadBookedTicket(1L)
    }

    @Test
    fun `티켓의 정보가 업데이트되면 뷰에 반영된다`() {
        verify {
            view.showBookedTicket(any())
        }
    }
}
