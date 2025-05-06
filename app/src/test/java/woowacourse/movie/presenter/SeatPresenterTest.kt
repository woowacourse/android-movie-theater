package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.mockk.verifyOrder
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.booking.AdmissionCount
import woowacourse.movie.domain.model.booking.Booking
import woowacourse.movie.domain.model.seat.Column
import woowacourse.movie.domain.model.seat.Row
import woowacourse.movie.domain.model.seat.Seat
import woowacourse.movie.domain.model.seat.Seats
import woowacourse.movie.fixture.SEAT_A1
import woowacourse.movie.fixture.SEAT_C1
import woowacourse.movie.fixture.SEAT_E1
import woowacourse.movie.view.home.seat.SeatContract
import woowacourse.movie.view.home.seat.SeatPresenter
import java.time.LocalDate
import java.time.LocalTime

class SeatPresenterTest {
    private lateinit var view: SeatContract.View
    private lateinit var seats: Seats
    private lateinit var presenter: SeatPresenter
    private val booking =
        Booking(
            movieTitle = "해리 포터와 마법사의 돌",
            screeningDate = LocalDate.now(),
            screeningTime = LocalTime.now(),
            count = AdmissionCount(3),
            theaterName = "선릉 극장",
        )

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        seats = mockk(relaxed = true)
        presenter = SeatPresenter(view, seats, booking)
    }

    @Test
    fun `좌석을 추가할 수 없으면 토스트 메시지를 보여준다`() {
        // given
        val position = Seat(Column(1), Row(1))
        every { seats.toggleSeat(any()) }

        // when
        presenter.changeSeat(position)

        // then
        verify { view.notifySelectedSeatsCount(booking.count.value) }
    }

    @Test
    fun `예매 버튼 클릭 시 선택된 좌석이 부족하면 토스트 메시지를 보여준다`() {
        // given
        every { seats.isNotSelectDone(booking.count.value) } returns true

        // when
        presenter.attemptConfirmBooking()

        // then
        verify { view.notifySelectedSeatsCount(booking.count.value) }
        verify(exactly = 0) { view.moveToBookingComplete(any()) }
    }

    @Test
    fun `예매 버튼 클릭 시 좌석이 충분하면 예매 완료 화면으로 이동한다`() {
        // given
        every { seats.isNotSelectDone(booking.count.value) } returns false
        every { seats.item } returns setOf(Seat(Column(1), Row(1)))
        every { seats.totalPrice() } returns 10000

        // when
        presenter.attemptConfirmBooking()

        // then
        verify { view.moveToBookingComplete(any()) }
    }

    @Test
    fun `B, S, A등급 순서로 좌석을 선택하면 가격이 10000, 25000, 37000원 순서로 표시된다`() {
        // given
        val presenter = SeatPresenter(view, Seats(), booking)

        // when
        presenter.changeSeat(SEAT_A1)
        presenter.changeSeat(SEAT_C1)
        presenter.changeSeat(SEAT_E1)

        // then
        verifyOrder {
            view.showPrice(10000)
            view.showPrice(25000)
            view.showPrice(37000)
        }
    }

    @Test
    fun `선택한 순서대로 좌석이 저장된다`() {
        // given
        val presenter = SeatPresenter(view, Seats(), booking)
        val capturedSeats = mutableListOf<Set<Seat>>()

        // when
        presenter.changeSeat(SEAT_A1)
        presenter.changeSeat(SEAT_C1)
        presenter.changeSeat(SEAT_E1)

        // then
        verify { view.showSeats(capture(capturedSeats)) }
        assertThat(capturedSeats[0]).isEqualTo(setOf(SEAT_A1))
        assertThat(capturedSeats[1]).isEqualTo(setOf(SEAT_A1, SEAT_C1))
        assertThat(capturedSeats[2]).isEqualTo(setOf(SEAT_A1, SEAT_C1, SEAT_E1))
    }
}
