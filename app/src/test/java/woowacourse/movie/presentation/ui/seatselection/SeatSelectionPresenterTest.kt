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
import woowacourse.movie.presentation.ui.utils.DummyData.MOVIE_ID
import woowacourse.movie.presentation.ui.utils.DummyData.THEATER_ID
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservationInfo
import woowacourse.movie.presentation.ui.utils.DummyData.dummyScreen
import woowacourse.movie.presentation.ui.utils.DummyData.dummySelectedSeatModel
import woowacourse.movie.presentation.ui.utils.DummyData.seatBoard

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
    fun `예약 정보와 영화 id로 데이터를 가져온다`() {
        // given

        every { screenRepository.findScreen(THEATER_ID, MOVIE_ID) } returns
            Result.success(dummyScreen)
        every { view.showSeatModel(any()) } just runs
        every { view.back() } just runs
        every { screenRepository.loadSeatBoard(THEATER_ID) } returns Result.success(seatBoard)

        // when
        presenter.updateUiModel(dummyReservationInfo, MOVIE_ID)

        // then
        verify { view.showSeatModel(any()) }
    }

    @Test
    fun `스크린을 불러오지 못하면 view에게 예외를 전달한다`() {
        // given
        val exception = NoSuchElementException()
        every { screenRepository.findScreen(THEATER_ID, MOVIE_ID) } returns
            Result.failure(exception)
        every { view.showToastMessage(e = exception) } just runs
        every { view.back() } just runs
        every { screenRepository.loadSeatBoard(THEATER_ID) } returns Result.success(seatBoard)
        every { view.showSeatModel(any()) } just runs

        // when
        presenter.updateUiModel(dummyReservationInfo, MOVIE_ID)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.showSeatModel(any()) }
        verify { view.back() }
    }

    @Test
    fun `좌석 배치를 불러오지 못하면 view에게 예외를 전달한다`() {
        // given
        val exception = NoSuchElementException()
        every { screenRepository.findScreen(THEATER_ID, MOVIE_ID) } returns
            Result.success(dummyScreen)
        every { screenRepository.loadSeatBoard(THEATER_ID) } returns Result.failure(exception)
        every { view.showToastMessage(e = exception) } just runs
        every { view.back() } just runs

        // when
        presenter.updateUiModel(dummyReservationInfo, MOVIE_ID)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.back() }
    }

    @Test
    fun `좌석이 선택되어 있으면 선택을 해제한 뒤 가격을 다시 계산한다`() {
        // given
        every { view.showSeatModel(any()) } just runs

        // when
        presenter.clickSeat(dummySelectedSeatModel)

        // then
        verify { view.showSeatModel(any()) }
    }

    @Test
    fun `예매 완료 시, view에게 메시지(ReservationSuccessMessage)와 결과 화면으로 이동하라는 정보를 전달한다()`() {
        // given
        every { view.showSeatModel(any()) } just runs
        every { screenRepository.loadSeatBoard(THEATER_ID) } returns Result.success(seatBoard)
        every { screenRepository.findScreen(THEATER_ID, MOVIE_ID) } returns Result.success(dummyScreen)
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

        // when
        presenter.updateUiModel(dummyReservationInfo, MOVIE_ID)
        presenter.reserve()

        // then
        verify { view.showToastMessage(MessageType.ReservationSuccessMessage) }
        verify { view.navigateToReservation(1) }
    }
}
