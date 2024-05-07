import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Theater
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationContract
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationPresenter
import woowacourse.movie.purchaseconfirmation.uimodel.toReservationResultUiModel
import woowacourse.movie.repository.MovieRepository

@ExtendWith(MockKExtension::class)
class PurchaseConfirmationPresenterTest {
    @MockK
    private lateinit var repository: MovieRepository

    @RelaxedMockK
    private lateinit var view: PurchaseConfirmationContract.View

    @InjectMockKs
    private lateinit var presenter: PurchaseConfirmationPresenter

    @Test
    fun `예매 내역을 보여준다`() {
        // given
        every { repository.reservationById(0) } returns Reservation.STUB
        every { repository.theaterById(Reservation.STUB.theaterId) } returns Theater.STUB_A
        // when
        presenter.loadReservationResult(0)
        // then
        val expectedResult = Reservation.STUB.toReservationResultUiModel(Theater.STUB_A.name)
        verify { view.showResult(expectedResult) }
    }
}
