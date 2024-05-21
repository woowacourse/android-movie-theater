package woowacourse.movie.ui.seat

import android.os.Handler
import android.os.Looper
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.FakeReservationRepository
import woowacourse.movie.data.repository.FakeScreenRepository

class SeatReservationPresenterTest {
    private lateinit var mockView: SeatReservationContract.View
    private lateinit var presenter: SeatReservationPresenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<SeatReservationContract.View>(relaxed = true)

        mockkStatic(Looper::class)
        every { Looper.getMainLooper() } returns mockk(relaxed = true)

        mockkConstructor(Handler::class)
        every { anyConstructed<Handler>().post(any()) } answers {
            firstArg<Runnable>().run()
            true
        }
    }

    @Test
    fun showSeats() {
        // given
        presenter = testSeatReservationPresenterFixture()

        // when
        presenter.loadAllSeats()

        // then
        verify(exactly = 1) { mockView.showAllSeats(any()) }
    }

    private fun testSeatReservationPresenterFixture(
        timeReservationId: Int = 0,
        theaterId: Int = 1,
    ): SeatReservationPresenter =
        SeatReservationPresenter(
            view = mockView,
            reservationRepository = FakeReservationRepository(),
            screenRepository = FakeScreenRepository(),
            timeReservationId = timeReservationId,
            theaterId = theaterId,
        )
}
