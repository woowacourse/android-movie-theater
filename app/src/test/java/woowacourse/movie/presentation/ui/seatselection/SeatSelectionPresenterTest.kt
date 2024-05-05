package woowacourse.movie.presentation.ui.seatselection

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservationInfo
import woowacourse.movie.presentation.ui.utils.DummyData.dummyScreen
import woowacourse.movie.presentation.ui.utils.DummyData.dummySeatBoard
import woowacourse.movie.presentation.ui.utils.DummyData.dummySeatModel

@ExtendWith(MockKExtension::class)
class SeatSelectionPresenterTest {
    @MockK
    private lateinit var view: SeatSelectionContract.View

    private lateinit var presenter: SeatSelectionContract.Presenter

    @MockK
    private lateinit var screenRepository: ScreenRepository

    @MockK
    private lateinit var reservationRepository: ReservationRepository

    @BeforeEach
    fun setUp() {
        presenter = SeatSelectionPresenter(view, screenRepository, reservationRepository)
    }

    @Test
    fun `SeatSelectionPresenter가 유효하지 않은 상영 id값으로 loadScreen()을 했을 때, view에게 back과 throwable를 전달한다`() {
        // given
        every { screenRepository.findByScreenId(any(), any()) } returns
            Result.failure(
                NoSuchElementException(),
            )
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadScreen(1)

        // then
        verify { view.showToastMessage(e = any()) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `SeatSelectionPresenter가 유효하지 않은 상영 id값으로 loadSeatBoard()를 했을 때, view에게 back과 throwable를 전달한다`() {
        // given
        every { screenRepository.loadSeatBoard(any()) } returns
            Result.failure(
                NoSuchElementException(),
            )
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadSeatBoard(1)

        // then
        verify { view.showToastMessage(e = any()) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `SeatSelectionPresenter가 loadSeatBoard()를 했을 때, 예기치 않은 오류가 발생하면 view에게 back과 throwable를 전달한다`() {
        // given
        every { screenRepository.loadSeatBoard(any()) } returns Result.failure(Exception())
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadSeatBoard(1)

        // then
        verify { view.showToastMessage(e = any()) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `SeatSelectionPresenter가 선택된 좌석들의 가격을 계산(calculateSeat) 했을 때, view에게 총 결제 금액에 대한 정보(showTotalPrice)를 전달한다`() {
        // given
        val reservationInfo = dummyReservationInfo
        every { screenRepository.loadSeatBoard(any()) } returns Result.success(dummySeatBoard)
        every { view.selectSeat(any()) } just runs
        every { view.showTotalPrice(any()) } just runs
        presenter.updateUiModel(reservationInfo)
        presenter.clickSeat(dummySeatModel)

        // when
        presenter.calculateSeat()

        // then
        verify { view.showTotalPrice(any()) }
    }

    @Test
    fun `SeatSelectionPresenter가 예매 완료 버튼을 누르면(reverse), view에게 메시지(ReservationSuccessMessage)와 결과 화면으로 이동하라는 정보를 전달한다()`() {
        // given
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every {
            reservationRepository.saveReservation(
                any(),
                any(),
                any(),
                any(),
                any(),
            )
        } returns Result.success(1)
        every { view.showToastMessage(MessageType.ReservationSuccessMessage) } just runs
        every { view.navigateToReservation(any()) } just runs
        every { view.showScreen(any(), any(), any()) } just runs

        // when
        presenter.loadScreen(1)
        presenter.updateUiModel(dummyReservationInfo)
        presenter.reserve()

        // then
        verify { view.showToastMessage(MessageType.ReservationSuccessMessage) }
        verify { view.navigateToReservation(1) }
    }

    @Test
    fun `SeatSelectionPresenter가 예매 정보가 잘못되었을 때 예매 완료 버튼을 누르면(reverse), view에게 메시지(Exception)와 뒤로 돌아가라고() 전달한다(back)`() {
        // given
        val exception = NoSuchElementException()
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every {
            reservationRepository.saveReservation(
                any(),
                any(),
                any(),
                any(),
                any(),
            )
        } returns Result.failure(exception)
        every { view.showSnackBar(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs
        every { view.showScreen(any(), any(), any()) } just runs

        // when
        presenter.loadScreen(1)
        presenter.updateUiModel(dummyReservationInfo)
        presenter.reserve()

        // then
        verify { view.showSnackBar(e = exception) }
        verify { view.navigateBackToPrevious() }
    }
}
