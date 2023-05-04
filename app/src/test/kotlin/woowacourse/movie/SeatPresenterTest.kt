package woowacourse.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ticket.SeatRank
import woowacourse.movie.ui.seat.SeatContract
import woowacourse.movie.ui.seat.SeatPresenter

class SeatPresenterTest {

    private lateinit var seatPresenter: SeatContract.Presenter
    private lateinit var view: SeatContract.View

    @Before
    fun setUp() {
        view = mockk()

        // given: 기본적으로 티켓은 한장이다.
        seatPresenter = SeatPresenter(
            view = view,
            bookedMovie = BookedMovie(
                ticketCount = 1
            )
        )
    }

    @Test
    fun `영화 이름 정보를 받아오면서 뷰의 영화 이름 텍스트를 초기화한다`() {
        // given
        val slot = slot<String>()
        every { view.initMovieTitleText(capture(slot)) } answers { println(slot.captured) }

        // when
        seatPresenter.initMovieTitle()

        //then
        val actual = slot.captured
        assertEquals(seatPresenter.movie.title, actual)
        verify { view.initMovieTitleText(actual) }
    }

    @Test
    fun `좌석을 선택하면 선택한 뷰는 가격을 나타내는 텍스트를 갱신한다`() {
        // given
        val slotPayment = slot<Int>()
        ignoreOnSelected(view)
        ignoreButtonState(view)
        every {
            view.setSeatPayment(capture(slotPayment))
        } answers {
            println("가격: %d".format(slotPayment.captured))
        }

        // when : B등급(10_000원)의 좌석을 선택했을 때
        seatPresenter.onSeatSelected(1, 1)

        //then
        val actual = slotPayment.captured

        assertEquals(SeatRank.B.price, actual)
        verify { view.setSeatPayment(actual) }
    }

    @Test
    fun `선택하지 않은 좌석을 선택하면 좌석을 나타내는 뷰는 해당 좌석의 선택 여부 설정한다`() {
        // given
        val slotRowPos = slot<Int>()
        val slotColPos = slot<Int>()
        ignoreButtonState(view)
        ignorePaymentText(view)
        every { view.setSeatSelected(capture(slotRowPos), capture(slotColPos)) } answers {
            println("row: %d, col: %d".format(slotRowPos.captured, slotColPos.captured))
        }

        // when
        seatPresenter.onSeatSelected(1, 1)

        //then
        val actualRow = slotRowPos.captured
        val actualCol = slotColPos.captured

        assertEquals(actualRow to actualCol, 1 to 1)
        verify { view.setSeatSelected(actualRow, actualCol) }
    }

    @Test
    fun `선택한 좌석을 다시 선택하면 좌석을 나타내는 뷰는 해당 좌석의 선택 여부를 다시 설정한다`() {
        // given
        val slotRowPos = slot<Int>()
        val slotColPos = slot<Int>()
        ignoreButtonState(view)
        ignorePaymentText(view)
        every { view.setSeatSelected(capture(slotRowPos), capture(slotColPos)) } answers {
            println("row: %d, col: %d".format(slotRowPos.captured, slotColPos.captured))
        }
        seatPresenter.onSeatSelected(1, 1)

        // when
        seatPresenter.onSeatSelected(1, 1)

        //then
        val actualRow = slotRowPos.captured
        val actualCol = slotColPos.captured

        assertEquals(actualRow to actualCol, 1 to 1)
        verify { view.setSeatSelected(actualRow, actualCol) }
    }

    @Test
    fun `티켓 수보다 더 많은 좌석을 선택하려고 시도하면 뷰는 좌석을 더이상 선택할 수 없다는 것을 나타낸다`() {
        // given
        ignoreOnSelected(view)
        ignoreButtonState(view)
        ignorePaymentText(view)
        every { view.showCannotSelectSeat() } answers { println("더 이상 좌석을 선택할 수 없음.") }
        seatPresenter.onSeatSelected(1, 1)

        // when
        seatPresenter.onSeatSelected(1, 2)

        //then
        verify { view.showCannotSelectSeat() }
    }

    @Test
    fun `좌석을 티켓 수만큼 선택을 완료하면 뷰는 해당 좌석의 선택 여부를 설정하고 버튼을 활성화시킨다`() {
        // given
        val slotButtonState = slot<Boolean>()
        ignoreOnSelected(view)
        ignorePaymentText(view)

        every { view.setButtonState(capture(slotButtonState)) } just Runs

        // when
        seatPresenter.onSeatSelected(1, 1)

        //then
        val actual = slotButtonState.captured

        assertEquals(true, actual)
        verify { view.setButtonState(actual) }
    }

    @Test
    fun `좌석들을 초기화하고 뷰에 좌석을 표시한다`() {
        // given
        val theater = seatPresenter.theater
        val slotRowSize = slot<Int>()
        val slotColSize = slot<Int>()

        every {
            view.initSeatTableView(
                rowSize = capture(slotRowSize),
                columnSize = capture(slotColSize)
            )
        } answers {
            println("rowSize: %d, colSize: %d".format(slotRowSize.captured, slotColSize.captured))
        }

        // when
        seatPresenter.initSelectedSeats()

        //then
        val actualRow = slotRowSize.captured
        val actualCol = slotColSize.captured

        assertEquals(actualRow to actualCol, theater.rowSize to theater.columnSize)
        verify { view.initSeatTableView(actualRow, actualCol) }
    }

    @Test
    fun `예약 완료 후 예약정보를 발생시킨다`() {
        // given
        val theater = seatPresenter.theater
        val slotRowSize = slot<Int>()
        val slotColSize = slot<Int>()

        every {
            view.initSeatTableView(
                rowSize = capture(slotRowSize),
                columnSize = capture(slotColSize)
            )
        } answers {
            println("rowSize: %d, colSize: %d".format(slotRowSize.captured, slotColSize.captured))
        }

        // when
        seatPresenter.initSelectedSeats()

        //then
        val actualRow = slotRowSize.captured
        val actualCol = slotColSize.captured

        assertEquals(actualRow to actualCol, theater.rowSize to theater.columnSize)
        verify { view.initSeatTableView(actualRow, actualCol) }
    }
}

