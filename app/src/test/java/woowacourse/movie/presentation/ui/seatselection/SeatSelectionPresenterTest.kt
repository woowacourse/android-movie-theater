package woowacourse.movie.presentation.ui.seatselection

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.NotificationRepository
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.ScreenRepository
import woowacourse.movie.presentation.model.MessageType
import woowacourse.movie.presentation.model.SeatModel
import woowacourse.movie.presentation.model.UserSeat
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservationInfo
import woowacourse.movie.presentation.ui.utils.DummyData.dummyScreen
import woowacourse.movie.presentation.ui.utils.DummyData.dummySeatBoard
import woowacourse.movie.presentation.ui.utils.DummyData.dummySeatModel

@ExtendWith(MockKExtension::class)
class SeatSelectionPresenterTest {
    @MockK
    private lateinit var view: SeatSelectionContract.View

    @MockK
    private lateinit var screenRepository: ScreenRepository

    @MockK
    private lateinit var reservationRepository: ReservationRepository

    @MockK
    private lateinit var notificationRepository: NotificationRepository

    @InjectMockKs
    private lateinit var presenter: SeatSelectionPresenter

    @Test
    fun `SeatSelectionPresenter가 유효하지 않은 상영 id값으로 loadScreen()을 했을 때, view에게 back과 throwable를 전달한다`() {
        // given
        val exception = NoSuchElementException()
        every { screenRepository.findByScreenId(any(), any()) } returns
            Result.failure(exception)
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadScreen(dummyReservationInfo)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `SeatSelectionPresenter가 유효한 상영 id값으로 loadSeatBoard()를 했을 때, view에게 좌석 정보와 클릭 설정에 대한 좌석 정보를 전달한다`() {
        // given
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { screenRepository.loadSeatBoard(any()) } returns Result.success(dummySeatBoard)
        every { view.showScreen(any(), any(), any()) } just runs
        every { view.showSeatBoard(any()) } just runs

        // when
        presenter.loadScreen(dummyReservationInfo)
        presenter.loadSeatBoard(1)

        // then
        val userSeat =
            UserSeat(dummySeatBoard.seats.map { SeatModel(it.column, it.row, it.seatRank) })
        verify { view.showSeatBoard(userSeat) }
    }

    @Test
    fun `SeatSelectionPresenter가 유효하지 않은 상영 id값으로 loadSeatBoard()를 했을 때, view에게 back과 throwable를 전달한다`() {
        // given
        val exception = NoSuchElementException()
        every { screenRepository.loadSeatBoard(any()) } returns Result.failure(exception)
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadSeatBoard(1)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `SeatSelectionPresenter가 loadSeatBoard()를 했을 때, 예기치 않은 오류가 발생하면 view에게 back과 throwable를 전달한다`() {
        // given
        val exception = Exception()
        every { screenRepository.loadSeatBoard(any()) } returns Result.failure(exception)
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadSeatBoard(1)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `SeatSelectionPresenter가 남은 좌석을 선택(clickSeat()) 했을 때, 선택할 티켓이 있다면 view에게 좌석을 선택하라는 정보(selectSeat)를 전달한다`() {
        // given
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { screenRepository.loadSeatBoard(any()) } returns Result.success(dummySeatBoard)
        every { view.showScreen(any(), any(), any()) } just runs
        every { view.showSeatBoard(any()) } just runs
        every { view.selectSeat(any()) } just runs
        presenter.loadScreen(dummyReservationInfo)
        presenter.loadSeatBoard(dummyReservationInfo.theaterId)

        // when
        presenter.clickSeat(dummySeatModel)

        // then
        var userSeat =
            UserSeat(dummySeatBoard.seats.map { SeatModel(it.column, it.row, it.seatRank, true) })

        val seatModels =
            userSeat.seatModels.map {
                if (dummySeatModel.column == it.column && dummySeatModel.row == it.row) {
                    it.copy(isSelected = true)
                } else {
                    it
                }
            }
        userSeat = userSeat.copy(seatModels = seatModels)
        verify { view.selectSeat(userSeat) }
    }

    @Test
    fun `SeatSelectionPresenter가 남은 좌석을 선택(clickSeat()) 했을 때, 선택할 티켓이 없다면 view에게 snackBar message(AllSeatsSelectedMessage)를 전달한다`() {
        // given
        val reservationInfo = dummyReservationInfo.copy(ticketCount = 0)
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { screenRepository.loadSeatBoard(any()) } returns Result.success(dummySeatBoard)
        every { view.showScreen(any(), any(), any()) } just runs
        every { view.showSnackBar(messageType = any()) } just runs

        // when
        presenter.loadScreen(reservationInfo)
        presenter.clickSeat(dummySeatModel)

        // then
        verify { view.showSnackBar(MessageType.AllSeatsSelectedMessage(reservationInfo.ticketCount)) }
    }

    @Test
    fun `SeatSelectionPresenter가 이미 선택된 좌석을 선택(clickSeat()) 했을 때, view에게 좌석 선택 해제라는 정보(unselectSeat)를 전달한다`() {
        // given
        var userSeat =
            UserSeat(dummySeatBoard.seats.map { SeatModel(it.column, it.row, it.seatRank) })
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { screenRepository.loadSeatBoard(any()) } returns Result.success(dummySeatBoard)
        every { view.showScreen(any(), any(), any()) } just runs
        every { view.showSeatBoard(any()) } just runs
        every { view.selectSeat(any()) } just runs
        every { view.unselectSeat(any()) } just runs
        presenter.loadScreen(dummyReservationInfo)
        presenter.loadSeatBoard(1)

        // when
        presenter.clickSeat(dummySeatModel.copy(isSelected = false))
        presenter.clickSeat(dummySeatModel.copy(isSelected = true))

        // then
        verify { view.unselectSeat(userSeat) }
    }

    @Test
    fun `SeatSelectionPresenter가 선택된 좌석들의 가격을 계산(calculateSeat) 했을 때, view에게 총 결제 금액에 대한 정보(showTotalPrice)를 전달한다`() {
        // given
        every { screenRepository.findByScreenId(any(), any()) } returns Result.success(dummyScreen)
        every { screenRepository.loadSeatBoard(any()) } returns Result.success(dummySeatBoard)
        every { view.showScreen(any(), any(), any()) } just runs
        every { view.showSeatBoard(any()) } just runs
        every { view.selectSeat(any()) } just runs
        every { view.showTotalPrice(any()) } just runs
        presenter.loadScreen(dummyReservationInfo)
        presenter.loadSeatBoard(0)
        presenter.clickSeat(dummySeatModel)

        // when
        presenter.calculateSeat()

        // then
        verify { view.showTotalPrice(dummySeatModel.seatRank.price) }
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
                any(),
            )
        } returns Result.success(1)
        every {
            notificationRepository.registerNotification(
                any(),
                any(),
                any(),
            )
        } returns Result.success(Unit)
        every { view.showToastMessage(MessageType.ReservationSuccessMessage) } just runs
        every { view.navigateToReservation(any()) } just runs
        every { view.showScreen(any(), any(), any()) } just runs

        // when
        presenter.loadScreen(dummyReservationInfo)
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
                any(),
            )
        } returns Result.failure(exception)
        every {
            notificationRepository.registerNotification(
                any(),
                any(),
                any(),
            )
        } returns Result.failure(IllegalArgumentException())
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs
        every { view.showScreen(any(), any(), any()) } just runs

        // when
        presenter.loadScreen(dummyReservationInfo)
        presenter.reserve()

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }
}
