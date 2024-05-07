package woowacourse.movie.model.ui.selection

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.data.ReservationsImpl
import woowacourse.movie.model.movie.Reservation
import woowacourse.movie.model.movie.ReservationCount
import woowacourse.movie.ui.selection.MovieSeatSelectionContract
import woowacourse.movie.ui.selection.MovieSeatSelectionPresenter
import java.time.LocalDateTime

class MovieSeatSelectionPresenterTest {
    private lateinit var presenter: MovieSeatSelectionPresenter
    private lateinit var view: MovieSeatSelectionContract.View

    @BeforeEach
    fun setUp() {
        view = mockk<MovieSeatSelectionContract.View>(relaxed = true)
        presenter = MovieSeatSelectionPresenter(view, ReservationsImpl, UserTicketsImpl)
        ReservationsImpl.save(
            Reservation(
                title = "",
                theater = "강남",
                screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 10, 0),
                reservationCount = ReservationCount(1),
            ),
        )
    }

    @Test
    fun `영화관 좌석정보를 불러온다`() {
        // given

        // when
        presenter.loadTheaterInfo(0L)

        // then
        verify { view.showMovieTitle(any()) }
        verify { view.showReservationTotalAmount(any()) }
        verify { view.showTheater(any(), any()) }
    }

    @Test
    fun `좌석을 선택한다`() {
        // given
        presenter.loadTheaterInfo(0L)

        // when
        presenter.selectSeat(1, 1)

        // then
        verify { view.showSelectedSeat(any()) }
        verify { view.updateSelectCompletion(any()) }
        verify { view.showReservationTotalAmount(any()) }
    }
}
