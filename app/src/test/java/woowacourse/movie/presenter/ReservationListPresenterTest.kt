package woowacourse.movie.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.dummyReservationInfoUiModel
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.presentation.view.reservationlist.ReservationListContract
import woowacourse.movie.presentation.view.reservationlist.ReservationListPresenter

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var view: ReservationListContract.View
    private lateinit var provider: ReservationRepository

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        provider = mockk()
        presenter = ReservationListPresenter(view, provider)
    }

    @Test
    fun `저장된 예매 내역을 화면에 보여준다`() {
        // given
        every { provider.getAllReservations() } returns emptyList()
        every { view.showReservations(any()) } just Runs

        // when
        presenter.fetchReservations()
        Thread.sleep(100)

        // then
        verify { view.showReservations(any()) }
    }

    @Test
    fun `예매 내역을 눌렀을 때 예매완료 화면으로 넘어간다`() {
        // given
        every { view.navigateToComplete(any()) } just Runs

        // when
        presenter.reservationSelected(dummyReservationInfoUiModel)

        // then
        verify { view.navigateToComplete(any()) }
    }
}
