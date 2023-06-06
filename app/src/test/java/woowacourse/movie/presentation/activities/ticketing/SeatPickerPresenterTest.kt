package woowacourse.movie.presentation.activities.ticketing

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.seat.DomainSeat
import woowacourse.movie.presentation.activities.main.fragments.history.HistoryDbHelper
import woowacourse.movie.presentation.activities.ticketing.seatpicker.SeatPickerContract
import woowacourse.movie.presentation.activities.ticketing.seatpicker.SeatPickerPresenter
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.PickedSeats
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketPrice
import woowacourse.movie.presentation.model.item.Reservation

internal class SeatPickerPresenterTest {
    private lateinit var presenter: SeatPickerPresenter
    private lateinit var view: SeatPickerContract.View
    private lateinit var db: HistoryDbHelper
    private lateinit var pickedSeats: DomainSeat

    @Before
    fun setUp() {
        view = mockk()
        db = mockk()
        presenter = SeatPickerPresenter(view, db)
    }

    @Test
    fun `영화 정보를 보여준다`() {
        // given
        every { view.showData() } just Runs

        // when
        presenter.updateView()

        // then
        verify { view.showData() }
    }

    @Test
    fun `버튼의 Enabled 속성을 true로 변경한다`() {
        // given
        every { view.setDoneBtnEnabled(true) } just Runs

        // when
        presenter.updateDoneBtnEnabled(true)

        // then
        verify { view.setDoneBtnEnabled(true) }
    }

    @Test
    fun `총 금액은 2000원이다`() {
        // given
        val expected = TicketPrice(2000)
        every { view.setTotalPriceView(expected) } just Runs

        // when
        presenter.updateTotalPriceView(TicketPrice(2000))

        // then
        verify { view.setTotalPriceView(TicketPrice(2000)) }
    }

    @Test
    fun `A1자리를 선택했으면 A1은 선택된 상태다`() {
        // given
        val newSeat = Seat(SeatRow('A'), SeatColumn(1))
        val expected = true

        // when
        presenter.pick(newSeat)
        val actual = presenter.isPicked(newSeat)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `5월 4일 12시 영화에서 A1자리만 선택했으면 총 금액은 10_000원이다`() {
        // given
        val newSeat = Seat(SeatRow('A'), SeatColumn(1))
        presenter.pick(newSeat)

        // when
        val actual = presenter.calculateTotalPrice(
            MovieDate(2023, 5, 4),
            MovieTime(12, 0),
        )

        val expected = TicketPrice(10_000)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `5월 10일 12시 영화에서 A1자리만 선택했으면 10% 할인되어 총 금액은 9_000원이다`() {
        // given
        val newSeat = Seat(SeatRow('A'), SeatColumn(1))
        presenter.pick(newSeat)

        // when
        val actual = presenter.calculateTotalPrice(
            MovieDate(2023, 5, 10),
            MovieTime(12, 0),
        )

        val expected = TicketPrice(9_000)

        // then
        assertEquals(expected, actual)
    }

    @Test
    fun `데이터베이스에 데이터를 추가한다`() {
        // given
        val reservation = Reservation(
            movieTitle = "가디언즈 오브 갤럭시",
            movieDate = MovieDate(2023, 5, 4),
            movieTime = MovieTime(21, 0),
            ticket = Ticket(2),
            seats = PickedSeats.from("A1, A2"),
            theaterName = "선릉 극장",
            ticketPrice = TicketPrice(20_000),
        )
        every { db.insertData(reservation) } just Runs

        // when
        presenter.insertData(reservation)

        // then
        verify(exactly = 1) { db.insertData(reservation) }
    }
}
