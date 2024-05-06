package woowacourse.movie.presentation.ui.reservation

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
import woowacourse.movie.presentation.ui.utils.DummyData.RESERVATION_ID
import woowacourse.movie.presentation.ui.utils.DummyData.THEATER_ID
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservation

@ExtendWith(MockKExtension::class)
class ReservationPresenterTest {
    @MockK
    private lateinit var view: ReservationContract.View

    private lateinit var presenter: ReservationContract.Presenter

    @MockK
    private lateinit var reservationRepository: ReservationRepository

    @MockK
    private lateinit var theaterRepository: ScreenRepository

    @BeforeEach
    fun setUp() {
        presenter = ReservationPresenter(view, reservationRepository, theaterRepository)
    }

    @Test
    fun `예매 id로 예매 정보를 불러와 뷰에 보여준다`() {
        // given
        val theaterName = "선릉"
        every { reservationRepository.findByReservationId(RESERVATION_ID) } returns
            Result.success(
                dummyReservation,
            )
        every { theaterRepository.findTheaterNameById(THEATER_ID) } returns
            Result.success(
                theaterName,
            )
        every { view.showReservation(any(), any()) } just runs

        // when
        presenter.loadReservation(RESERVATION_ID)

        // then
        val result = ReservationModel(theaterName = theaterName, reservation = dummyReservation)
        verify { view.showReservation(result, theaterName) }
    }

    @Test
    fun `유효하지 않은 예매 id라면 뷰에게 예외를 전달하고 이전 화면으로 간다`() {
        // given
        every { reservationRepository.findByReservationId(RESERVATION_ID) } returns
            Result.failure(
                NoSuchElementException(),
            )
        every { view.showToastMessage(e = any()) } just runs
        every { view.finishReservation() } just runs

        // when
        presenter.loadReservation(RESERVATION_ID)

        // then
        verify { view.showToastMessage(e = any()) }
        verify { view.finishReservation() }
    }

    @Test
    fun `유효하지 않은 상영관 id라면 뷰에게 예외를 전달하고 이전 화면으로 간다`() {
        // given
        every { reservationRepository.findByReservationId(RESERVATION_ID) } returns
            Result.success(
                dummyReservation,
            )

        every { theaterRepository.findTheaterNameById(THEATER_ID) } returns
            Result.failure(
                NoSuchElementException(),
            )

        every { view.showToastMessage(e = any()) } just runs
        every { view.finishReservation() } just runs

        // when
        presenter.loadReservation(RESERVATION_ID)

        // then
        verify { view.showToastMessage(e = any()) }
        verify { view.finishReservation() }
    }
}
