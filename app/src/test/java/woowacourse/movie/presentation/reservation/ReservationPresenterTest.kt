package woowacourse.movie.presentation.reservation

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.Reservation

@ExtendWith(MockKExtension::class)
class ReservationPresenterTest {
    @MockK
    private lateinit var repository: MovieRepository

    @RelaxedMockK
    private lateinit var view: ReservationContract.View

    @RelaxedMockK
    private lateinit var reservation: Reservation

    @InjectMockKs
    private lateinit var presenter: ReservationPresenter

    @Test
    fun `loadReservations를 호출하면 데이터가 반영되어 view의 showReservations로 전달된다`() {
        every { repository.loadReservedMovies() } returns Result.success(listOf(reservation))

        presenter.loadReservations()

        verify { view.showReservations(listOf(reservation)) }
    }

    @Test
    fun `loadReservations를 호출하여 오류가 발생했다면 view의 showError로 전달된다`() {
        every { repository.loadReservedMovies() } returns Result.failure(Throwable())

        presenter.loadReservations()

        verify { view.showError() }
    }

    @Test
    fun `findReservation을 호출하면 view의 navigateToConfirmPurchaseView로 reservationId가 전달된다`() {
        presenter.findReservation(1L)

        verify { view.navigateToConfirmPurchaseView(1L) }
    }
}
