import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Reservation
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationContract
import woowacourse.movie.purchaseconfirmation.PurchaseConfirmationPresenter
import woowacourse.movie.purchaseconfirmation.uimodel.toPurchaseConfirmationUiModel
import woowacourse.movie.usecase.FetchReservationWithIdUseCase

@ExtendWith(MockKExtension::class)
class PurchaseConfirmationPresenterTest {
    @RelaxedMockK
    private lateinit var view: PurchaseConfirmationContract.View

    @MockK
    private lateinit var fetchReservationWithIdUseCase: FetchReservationWithIdUseCase

    @InjectMockKs
    private lateinit var presenter: PurchaseConfirmationPresenter

    @Test
    fun `예매 내역을 보여준다`() {
        // given
        every { fetchReservationWithIdUseCase(any()).getOrNull() } returns Reservation.STUB
        // when
        presenter.loadReservationResult(0)
        // then
        val expectedResult = Reservation.STUB.toPurchaseConfirmationUiModel()
        verify { view.showResult(expectedResult) }
    }
}
