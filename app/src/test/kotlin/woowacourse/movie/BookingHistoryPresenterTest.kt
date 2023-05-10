package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.ReservationUiModel
import woowacourse.movie.ui.bookinghistory.BookingHistoryContract
import woowacourse.movie.ui.bookinghistory.BookingHistoryPresenter
import woowacourse.movie.ui.bookinghistory.BookingHistoryRepository

class BookingHistoryPresenterTest {

    private lateinit var bookingHistoryPresenter: BookingHistoryContract.Presenter
    private lateinit var view: BookingHistoryContract.View
    private lateinit var repository: BookingHistoryRepository

    @Before
    fun setUp() {
        view = mockk()
        repository = mockk()
        bookingHistoryPresenter = BookingHistoryPresenter(
            view = view,
            repository = repository
        )
    }

    @Test
    fun `예매 내역을 받아오면 뷰의 리사이클러뷰에 예매 내역을 주입해준다`() {
        // given
        val slotHistories = slot<List<ReservationUiModel>>()

        every { repository.loadBookingHistory() } returns listOf(
            ReservationUiModel(),
            ReservationUiModel()
        )
        every { view.initAdapter(capture(slotHistories)) } just Runs

        // when
        bookingHistoryPresenter.initBookingHistories()

        // then
        val actual = slotHistories.captured

        assertEquals(
            listOf(ReservationUiModel(), ReservationUiModel()),
            actual
        )
        verify { view.initAdapter(actual) }
    }
}