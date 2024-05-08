package woowacourse.movie.presentation.ui.reservation

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.repository.TheaterRepository
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservation

@ExtendWith(MockKExtension::class)
class ReservationPresenterTest {
    @MockK
    private lateinit var view: ReservationContract.View

    @MockK
    private lateinit var repository: ReservationRepository

    @MockK
    private lateinit var theaterRepository: TheaterRepository

    @InjectMockKs
    private lateinit var presenter: ReservationPresenter

    @Test
    fun `ReservationPresenter가 유효한 예매 id를 통해 loadReservation()을 했을 때, view에게 reservation 데이터를 전달한다`() {
        // given
        val theaterName = "선릉 극장"
        every { theaterRepository.findTheaterNameById(any()) } returns Result.success(theaterName)
        every { repository.findReservation(any()) } returns Result.success(dummyReservation)
        every { view.showReservation(any(), any()) } just runs

        // when
        presenter.loadReservation(1)

        // then
        verify { view.showReservation(dummyReservation, theaterName) }
    }

    @Test
    fun `ScreenPresenter가 유효하지 않은 예매 id를 통해 loadReservation()했을 때, view에게 back과 throwable를 전달한다`() {
        // given
        val exception = NoSuchElementException()
        every { repository.findReservation(any()) } returns Result.failure(exception)
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadReservation(1)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }

    @Test
    fun `ScreenPresenter가 loadReservation()했을 때, 예상치 못한 에러가 발생하면 view에게 back과 throwable를 전달한다`() {
        // given
        val exception = Exception()
        every { repository.findReservation(any()) } returns Result.failure(exception)
        every { view.showToastMessage(e = any()) } just runs
        every { view.navigateBackToPrevious() } just runs

        // when
        presenter.loadReservation(1)

        // then
        verify { view.showToastMessage(e = exception) }
        verify { view.navigateBackToPrevious() }
    }
}
