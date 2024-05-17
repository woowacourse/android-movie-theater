package woowacourse.movie.ui.seat

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
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
        mockView = mockk<SeatReservationContract.View>()
        presenter =
            SeatReservationPresenter(
                view = mockView,
                screenRepository = FakeScreenRepository(),
                reservationRepository = FakeReservationRepository(),
            )
    }

    @Test
    fun `좌석들을_보여준다`() {
        // given
        every { mockView.showAllSeats(any()) } just runs
        every { mockView.updateTotalPrice(any()) } just runs

        // when
        presenter.loadAllSeats()

        // then
        verify(exactly = 1) { mockView.showAllSeats(any()) }
    }
}
