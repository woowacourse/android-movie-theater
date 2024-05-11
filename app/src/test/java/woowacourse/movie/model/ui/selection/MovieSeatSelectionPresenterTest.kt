package woowacourse.movie.model.ui.selection

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.model.RESERVATION_DETAIL
import woowacourse.movie.model.ui.repository.FakeTicketRepository
import woowacourse.movie.ui.selection.MovieSeatSelectionContract
import woowacourse.movie.ui.selection.MovieSeatSelectionPresenter

class MovieSeatSelectionPresenterTest {
    private lateinit var presenter: MovieSeatSelectionPresenter
    private lateinit var view: MovieSeatSelectionContract.View

    @BeforeEach
    fun setUp() {
        view = mockk<MovieSeatSelectionContract.View>(relaxed = true)
        presenter = MovieSeatSelectionPresenter(view, FakeTicketRepository)
    }

    @Test
    fun `영화관 좌석정보를 불러온다`() {
        // given

        // when
        presenter.loadTheaterInfo(RESERVATION_DETAIL)

        // then
        verify { view.showReservationTotalAmount(any()) }
        verify { view.showTheater(any(), any()) }
    }

    @Test
    fun `좌석을 선택한다`() {
        // given
        presenter.loadTheaterInfo(RESERVATION_DETAIL)

        // when
        presenter.selectSeat(1, 1)

        // then
        verify { view.showSelectedSeat(any()) }
        verify { view.updateSelectCompletion(any()) }
        verify { view.showReservationTotalAmount(any()) }
    }
}
