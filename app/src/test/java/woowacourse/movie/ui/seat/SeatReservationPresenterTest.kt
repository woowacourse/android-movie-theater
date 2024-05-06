package woowacourse.movie.ui.seat

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.repository.FakeReservationRepository
import woowacourse.movie.domain.repository.FakeScreenRepository

class SeatReservationPresenterTest {
    private lateinit var mockView: SeatReservationContract.View
    private lateinit var presenter: SeatReservationPresenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<SeatReservationContract.View>(relaxed = true)
        presenter =
            SeatReservationPresenter(
                view = mockView,
                screenRepository = FakeScreenRepository(),
                reservationRepository = FakeReservationRepository(),
            )
    }

    @Test
    fun showSeats() {
        // when
        presenter.loadAllSeats()

        // then
        verify(exactly = 1) { mockView.showAllSeats(any()) }
    }
}
