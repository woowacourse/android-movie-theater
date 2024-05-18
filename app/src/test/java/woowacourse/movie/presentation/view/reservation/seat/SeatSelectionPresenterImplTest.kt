package woowacourse.movie.presentation.view.reservation.seat

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.db.ReservationTicketDao
import woowacourse.movie.domain.repository.SeatRepository
import woowacourse.movie.presentation.reservation.seat.SeatSelectionContract
import woowacourse.movie.presentation.reservation.seat.SeatSelectionPresenterImpl

class SeatSelectionPresenterImplTest {
    private lateinit var view: SeatSelectionContract.View
    private lateinit var presenter: SeatSelectionPresenterImpl
    private lateinit var seatRepository: SeatRepository
    private lateinit var ticketDao: ReservationTicketDao

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        seatRepository = mockk(relaxed = true)
        ticketDao = mockk(relaxed = true)

        presenter = SeatSelectionPresenterImpl(1, seatRepository, ticketDao)
        presenter.attachView(view)
    }

    @Test
    fun `좌석 정보를 불러와서 화면에 보여준다`() {
        // given
        every { view.showSeatingChart(any(), any(), any()) } just Runs

        // when
        presenter.loadSeatingChart()

        // then
        verify { view.showSeatingChart(any(), any(), any()) }
    }

    @Test
    fun `좌석을 선택하면 총 금액과 확인 버튼의 활성 상태를 변경한다`() {
        // given
        every { view.updateTotalPrice(any()) } just Runs
        every { view.changeConfirmClickable(any()) } just Runs

        // when
        presenter.selectSeat(0, 0)

        // then
        verify(exactly = 1) { view.updateTotalPrice(any()) }
        verify(exactly = 1) { view.changeConfirmClickable(any()) }
    }
}
