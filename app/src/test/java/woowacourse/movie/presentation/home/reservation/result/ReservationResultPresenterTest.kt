package woowacourse.movie.presentation.home.reservation.result

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.presentation.common.model.SeatTypeUiModel
import woowacourse.movie.presentation.common.model.SeatUiModel
import woowacourse.movie.presentation.common.model.TicketUiModel
import java.time.LocalDateTime

class ReservationResultPresenterTest {
    private lateinit var view: ReservationResultContract.View
    private lateinit var presenter: ReservationResultContract.Presenter
    private val ticket =
        TicketUiModel(
            "해리포터",
            "선릉 극장",
            LocalDateTime.of(2025, 4, 1, 0, 0),
            seats = listOf(SeatUiModel(1, 1, SeatTypeUiModel.B_CLASS)),
            1,
            10_000,
        )

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = ReservationResultPresenter(view)
    }

    @Test
    fun `티켓 정보를 불러온다`() {
        // Given: View가 setScreen 동작을 설정한다
        every { view.showScreen(any(), any()) } just Runs

        // When: presenter가 데이터를 불러온다
        presenter.fetchDate(ticket)

        // Then: View의 setScreen이 티켓 번들의 정보와 함께 호출된다.
        verify { view.showScreen(any(), any()) }
    }
}
