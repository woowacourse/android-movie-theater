package woowacourse.movie.presentation.purchaseConfirmation

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
class PurchaseConfirmationPresenterTest {
    @MockK
    private lateinit var repository: MovieRepository

    @MockK
    private lateinit var view: PurchaseConfirmationContract.View

    @InjectMockKs
    private lateinit var presenter: PurchaseConfirmationPresenter

    @RelaxedMockK
    private lateinit var reservation: Reservation

    @Test
    fun `loadReservation을 호출하면 repository에서 데이터를 가져와 view의 showReservation으로 전달한다`() {
        every { repository.loadReservedMovie(any()) } returns Result.success(reservation)

        presenter.loadReservation(1)

        verify { view.showReservation(reservation) }
    }

    fun `loadReservation을 호출하여 오류가 발생하면 view의 showError를 호출한다`() {
        every { repository.loadReservedMovie(any()) } returns Result.failure(Throwable())

        presenter.loadReservation(1)

        verify { view.showError() }
    }
}
