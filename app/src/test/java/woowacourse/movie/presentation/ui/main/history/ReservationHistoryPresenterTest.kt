package woowacourse.movie.presentation.ui.main.history

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
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservation

@ExtendWith(MockKExtension::class)
class ReservationHistoryPresenterTest {
    @MockK
    private lateinit var view: ReservationHistoryContract.View

    @MockK
    private lateinit var repository: ReservationRepository

    @InjectMockKs
    private lateinit var presenter: ReservationHistoryPresenter

    @Test
    fun `ReservationPresenter가 loadReservation()을 했을 때, view에게 reservation 데이터와 theaterName을 전달한다`() {
        // given
        every { repository.getReservations() } returns Result.success(listOf(dummyReservation))
        every { view.showReservations(any()) } just runs

        // when
        presenter.loadReservations()

        // then
        verify { view.showReservations(listOf(dummyReservation)) }
    }

    @Test
    fun `ReservationPresenter가 loadReservation()을 했을 때, 예외가 발생하면 view에게 예외를 전달한다`() {
        // given
        val exception = Exception()
        every { repository.getReservations() } returns Result.failure(exception)
        every { view.showToastMessage(e = any()) } just runs

        // when
        presenter.loadReservations()

        // then
        verify { view.showToastMessage(exception) }
    }

    @Test
    fun `ReservationPresenter가 onReservationClick()을 했을 때, view에게 reservationId를 전달한다`() {
        // given
        every { repository.getReservations() } returns Result.success(listOf(dummyReservation))
        every { view.navigateToReservation(any()) } just runs

        // when
        presenter.onReservationClick(dummyReservation.reservationId)

        // then
        verify { view.navigateToReservation(dummyReservation.reservationId) }
    }
}
