package woowacourse.movie.ui.activity.seatpicker.presenter

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ui.activity.seatpicker.SeatPickerContract
import woowacourse.movie.ui.model.MovieTicketModel
import woowacourse.movie.ui.model.PeopleCountModel
import woowacourse.movie.ui.model.PriceModel
import woowacourse.movie.ui.model.TicketTimeModel
import woowacourse.movie.ui.model.seat.ColumnModel
import woowacourse.movie.ui.model.seat.RankModel
import woowacourse.movie.ui.model.seat.RowModel
import woowacourse.movie.ui.model.seat.SeatModel
import java.time.LocalDateTime

internal class SeatPickerPresenterTest {
    private lateinit var presenter: SeatPickerPresenter
    private val ticket = MovieTicketModel(
        "글로의 50가지 그림자",
        TicketTimeModel(LocalDateTime.now()),
        PeopleCountModel(1),
        emptySet(),
        PriceModel(0)
    )
    private lateinit var view: SeatPickerContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = SeatPickerPresenter(view)

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
        presenter.reserveSeat(seat)

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
        presenter.reserveSeat(seat1)

        // when
        val seat2 = SeatModel(RowModel.of(1), ColumnModel.of(2), RankModel.A)
        presenter.reserveSeat(seat2)

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
        presenter.cancelSeat(seat)

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
        presenter.reserveSeat(seat1)

        // when
        presenter.checkSelectionDone()

        // then
        verify { view.activateDoneButton() }
    }
}
