package woowacourse.movie.presentation.ui.main.history

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
import woowacourse.movie.presentation.ui.utils.DummyData.dummyReservations

@ExtendWith(MockKExtension::class)
class ReservationHistoryPresenterTest {
    @MockK
    private lateinit var view: ReservationHistoryContract.View

    @MockK
    private lateinit var repository: ReservationRepository

    private lateinit var presenter: ReservationHistoryContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = ReservationHistoryPresenter(view, repository)
    }

    @Test
    fun `예약_데이터_불러오기에_성공하면_뷰에게_넘겨준다`() {
        // given
        every { repository.findAll() } returns Result.success(dummyReservations)
        every { view.showReservations(dummyReservations) } just runs

        // when
        presenter.fetchData()

        // then
        verify { view.showReservations(dummyReservations) }
    }
}
