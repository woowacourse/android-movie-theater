package woowacourse.movie.ui.selection

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.RESERVATION_DETAIL
import woowacourse.movie.ui.repository.FakeTicketRepository

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
        verify { view.showReservationInfo(any(), any(), any()) }
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
