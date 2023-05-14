package woowacourse.movie.ui.activity.seatpicker.presenter

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.createMovieTicketModel
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.ui.activity.seatpicker.SeatPickerContract
import woowacourse.movie.ui.model.seat.ColumnModel
import woowacourse.movie.ui.model.seat.RankModel
import woowacourse.movie.ui.model.seat.RowModel
import woowacourse.movie.ui.model.seat.SeatModel

internal class SeatPickerPresenterTest {
    private lateinit var presenter: SeatPickerPresenter
    private lateinit var repository: ReservationRepository
    private val ticket = createMovieTicketModel()
    private lateinit var view: SeatPickerContract.View

    @Before
    fun setUp() {
        repository = mockk()
        view = mockk()
        presenter = SeatPickerPresenter(repository, view)

        justRun { view.applyTicketData(any()) }
        presenter.initTicket(ticket)
    }

    @Test
    fun `티켓을 초기화 하면 뷰에 티켓 데이터가 적용된다`() {
        // then
        verify { view.applyTicketData(any()) }
    }

    @Test
    fun `좌석 예약이 가능하면 좌석이 선택되고 티켓 금액이 업데이트 된다`() {
        // given
        justRun { view.setSeatReserved(any()) }
        justRun { view.updatePrice(any()) }

        // when
        val seat = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.A)
        presenter.handleSeatSelection(false, seat)

        // then
        verify {
            view.setSeatReserved(any())
            view.updatePrice(any())
        }
    }

    @Test
    fun `더 이상 좌석 예약이 불가능하면 사용자에게 알려준다`() {
        // given
        justRun { view.setSeatReserved(any()) }
        justRun { view.updatePrice(any()) }
        justRun { view.notifyUnableToReserveMore() }
        val seat1 = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.A)
        presenter.handleSeatSelection(false, seat1)

        // when
        val seat2 = SeatModel(RowModel.of(1), ColumnModel.of(2), RankModel.A)
        presenter.handleSeatSelection(false, seat2)

        // then
        verify { view.notifyUnableToReserveMore() }
    }

    @Test
    fun `좌석을 취소하면 좌석 선택이 취소되고 티켓 금액이 업데이트 된다`() {
        // given
        justRun { view.setSeatCanceled(any()) }
        justRun { view.updatePrice(any()) }

        // when
        val seat = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.A)
        presenter.handleSeatSelection(true, seat)

        // then
        verify {
            view.setSeatCanceled(any())
            view.updatePrice(any())
        }
    }

    @Test
    fun `좌석 선택이 완료되지 않았으면 완료 버튼이 비활성화 된다`() {
        // given
        justRun { view.deactivateDoneButton() }

        // when
        presenter.checkSelectionDone()

        // then
        verify { view.deactivateDoneButton() }
    }

    @Test
    fun `좌석 선택이 완료되었으면 완료 버튼이 활성화 된다`() {
        // given
        justRun { view.setSeatReserved(any()) }
        justRun { view.updatePrice(any()) }
        justRun { view.activateDoneButton() }
        val seat1 = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.A)
        presenter.handleSeatSelection(false, seat1)

        // when
        presenter.checkSelectionDone()

        // then
        verify { view.activateDoneButton() }
    }

    @Test
    fun addReservation() {
        // given
        justRun { repository.insertReservation(any()) }
//        every { database.query(any(), any(), any(), any(), any(), any(), any()) } returns null
//        justRun { database.close() }
        justRun { view.afterReservation(any()) }

        // when
        presenter.completeReservation()

        // then
        verify { view.afterReservation(any()) }
    }
}
