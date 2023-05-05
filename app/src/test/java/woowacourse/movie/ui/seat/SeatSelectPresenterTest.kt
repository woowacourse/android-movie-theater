package woowacourse.movie.ui.seat

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import org.junit.Test
import woowacourse.movie.model.CountState
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.SeatPositionState
import woowacourse.movie.model.SeatSelectState
import woowacourse.movie.model.TicketsState

class SeatSelectPresenterTest {
    @Test
    fun `좌석 선택 화면을 초기화한다`() {
        // given
        val view = mockk<SeatSelectContract.View>()
        val presenter = SeatSelectPresenter(view)
        val seatSelectState = sampleSeatSelectState
        every { view.initSeatTable(any()) } returns Unit
        every { view.showSeatSelectState(any()) } returns Unit
        every { view.setMoneyText(any()) } returns Unit

        // when
        presenter.init(seatSelectState, "")

        // then
        verify { view.showSeatSelectState(seatSelectState) }
        verify { view.initSeatTable(seatSelectState) }
    }

    @Test
    fun `좌석을 선택하면 금액이 바뀐다`() {
        // given
        val seatPositionState = mockk<List<SeatPositionState>>(relaxed = true)
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val presenter = SeatSelectPresenter(view)
        presenter.init(sampleSeatSelectState, "")
        every { view.showSeatSelectState(any()) } returns Unit

        // when
        presenter.discountApply(seatPositionState)

        //
        verify { view.setMoneyText(any()) }
    }

    @Test
    fun `티켓을 추가하면 확인페이지로 넘어간다`() {
        // given
        val seatPositionState = mockk<List<SeatPositionState>>(relaxed = true)
        val view = mockk<SeatSelectContract.View>(relaxed = true)
        val presenter = SeatSelectPresenter(view)
        presenter.init(sampleSeatSelectState, "")
        every { view.navigateToConfirmView(any()) } returns Unit

        // when
        presenter.addTicket(seatPositionState)

        //
        verify { view.navigateToConfirmView(any()) }
    }

    companion object {
        val sampleTicketsState = TicketsState(
            cinemaName = "cinemaName",
            MovieState(
                1,
                "title",
                LocalDate.MIN,
                LocalDate.MIN.plusDays(3),
                listOf(LocalTime.parse("10:00"), LocalTime.parse("12:00")),
                152,
                "description"
            ),
            LocalDateTime.MIN,
            listOf(SeatPositionState(1, 1))
        )
        val sampleSeatSelectState = SeatSelectState(
            sampleTicketsState.movieState,
            LocalDateTime.MIN,
            countState = CountState.of(10)
        )
    }
}
