package woowacourse.movie.presentation.view.main.booklist

import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Test
import woowacourse.movie.data.database.MovieHelper
import woowacourse.movie.presentation.model.ReservationResult

internal class BookingListPresenterTest {
    @Test
    fun `예매_내역을_가져온다`() {
        val view = mockk<BookingListContract.View>(relaxed = true)
        val movieHelper = mockk<MovieHelper>()
        val presenter = BookingListPresenter(view, mockk(relaxed = true))

        every { movieHelper.readBookingData() } returns listOf()
        val slot = slot<List<ReservationResult>>()
        justRun { view.showReservationListView(capture(slot)) }

        presenter.getReservationList()


        assertEquals(listOf<ReservationResult>(), slot.captured)
        verify { view.showReservationListView(listOf()) }
    }
}