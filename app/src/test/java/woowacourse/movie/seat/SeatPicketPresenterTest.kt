package woowacourse.movie.seat

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.model.seat.ColumnModel
import woowacourse.movie.model.seat.RankModel
import woowacourse.movie.model.seat.RowModel
import woowacourse.movie.model.seat.SeatModel
import woowacourse.movie.view.seat.SeatPickerContract
import woowacourse.movie.view.seat.SeatPicketPresenter
import java.time.LocalDateTime

class SeatPicketPresenterTest {
    private lateinit var presenter: SeatPickerContract.Presenter
    private lateinit var view: SeatPickerContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = SeatPicketPresenter(view)

        justRun { view.setTicketViews(dummyTicket) }
        justRun { view.setEnabledDone(false) }

        presenter.setupTicket(dummyTicket)
    }

    @Test
    fun `B급 좌석 하나를 선택하면 티켓 가격은 10_000원이다`() {
        // given
        val seat = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.B)
        val slot = slot<PriceModel>()

        justRun { view.setSelectedSeat(seat, true) }
        justRun { view.setTextPrice(capture(slot)) }

        // when
        presenter.addSeat(seat)

        // then
        val actual = slot.captured
        assertEquals(10_000, actual.amount)
        verify { view.setSelectedSeat(seat, true) }
        verify { view.setTextPrice(actual) }
    }

    @Test
    fun `같은 B급 좌석 하나를 두 번 선택하면 티켓 가격은 0원이다`() {
        // given
        val seat = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.B)
        val slot = slot<PriceModel>()

        justRun { view.setSelectedSeat(seat, true) }
        justRun { view.setSelectedSeat(seat, false) }
        justRun { view.setTextPrice(capture(slot)) }

        // when
        presenter.addSeat(seat)
        presenter.cancelSeat(seat)

        // then
        val actual = slot.captured
        assertEquals(0, actual.amount)
        verify { view.setSelectedSeat(seat, true) }
        verify { view.setSelectedSeat(seat, false) }
        verify { view.setTextPrice(actual) }
    }

    @Test
    fun `예매 인원이 2명일 때 두 개의 좌석을 선택하면 예매가 가능하다`() {
        // given
        val seatFirst = SeatModel(RowModel.of(1), ColumnModel.of(1), RankModel.B)
        val seatSecond = SeatModel(RowModel.of(1), ColumnModel.of(2), RankModel.B)
        val slotPrice = slot<PriceModel>()
        val slotCanReserve = slot<Boolean>()

        justRun { view.setSelectedSeat(seatFirst, true) }
        justRun { view.setSelectedSeat(seatSecond, true) }
        justRun { view.setTextPrice(capture(slotPrice)) }
        justRun { view.setEnabledDone(capture(slotCanReserve)) }

        // when
        presenter.addSeat(seatFirst)
        presenter.addSeat(seatSecond)
        presenter.canReserveSeat()

        // then
        val actual = slotCanReserve.captured
        assertEquals(true, actual)
        verify { view.setSelectedSeat(seatFirst, true) }
        verify { view.setSelectedSeat(seatSecond, true) }
        verify { view.setTextPrice(slotPrice.captured) }
        verify { view.setEnabledDone(actual) }
    }

    companion object {
        private val dummyTicket = MovieTicketModel(
            "그레이의 50가지 그림자 1",
            TicketTimeModel(LocalDateTime.of(2023, 5, 1, 13, 0)),
            PeopleCountModel(2),
            emptySet(),
            PriceModel(0)
        )
    }
}
