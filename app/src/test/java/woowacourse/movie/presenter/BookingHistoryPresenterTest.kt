package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.database.AppDatabase
import woowacourse.movie.ui.history.contract.BookingHistoryContract
import woowacourse.movie.ui.history.presenter.BookingHistoryPresenter

class BookingHistoryPresenterTest {
    private lateinit var appDatabase: AppDatabase
    private lateinit var presenter: BookingHistoryPresenter
    private lateinit var view: BookingHistoryContract.View

    @BeforeEach
    fun setUp() {
        appDatabase = mockk(relaxed = true)
        view = mockk(relaxed = true)
        presenter = BookingHistoryPresenter(view, appDatabase)
    }

    @Test
    fun `loadBookedTickets는 DB에서 데이터를 가져와 view에 전달한다`() {
        presenter.loadBookedTickets()

        verify { view.setBookedTicketItems(any()) }
    }
}
