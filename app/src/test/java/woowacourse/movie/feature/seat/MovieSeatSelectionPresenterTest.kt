package woowacourse.movie.feature.seat

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.reservation.ReservationRoomRepository
import woowacourse.movie.data.reservation.dto.Reservation
import woowacourse.movie.data.ticket.FakeTicketRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.reservation
import woowacourse.movie.feature.reservationCount
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName

class MovieSeatSelectionPresenterTest {
    private lateinit var view: MovieSeatSelectionContract.View
    private lateinit var presenter: MovieSeatSelectionPresenter
    private lateinit var ticketRepository: TicketRepository
    private lateinit var reservationRepository: ReservationRepository

    @BeforeEach
    fun setUp() {
        view = mockk()
        ticketRepository = FakeTicketRepository()
        reservationRepository = ReservationRoomRepository
        presenter = MovieSeatSelectionPresenter(view)
        presenter.updateSelectedSeats(selectedSeats)
    }

    @Test
    fun `예매 정보 id에 맞는 예매 정보를 불러온다`() {
        // given
        val reservationSlot = slot<Reservation>()
        every { view.setUpReservation(capture(reservationSlot)) } just runs
        val reservationId = reservationRepository.save(movieId, screeningDate, screeningTime, reservationCount, theaterName)

        // when
        presenter.loadReservation(reservationRepository, reservationId)

        // then
        val actual = reservationSlot.captured
        assertThat(actual.id).isEqualTo(reservationId)
        verify { view.setUpReservation(actual) }
    }

    @Test
    fun `존재하지 않는 예매 정보 id의 경우 예외가 발생한다`() {
        // given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // when
        presenter.loadReservation(reservationRepository, -1L)

        // then
        verify { view.showToastInvalidMovieIdError(any()) }
    }

    @Test
    fun `영화 좌석 정보를 초기화한다`() {
        // given
        every { view.setUpTableSeats(any()) } just runs

        // when
        presenter.loadTableSeats(selectedSeats)

        // then
        verify { view.setUpTableSeats(any()) }
    }

    @Test
    fun `좌석을 선택한다`() {
        // given
        every { view.updateSeatBackgroundColor(any(), any()) } just runs
        every { view.updateSelectResult(any()) } just runs

        // when
        presenter.selectSeat(0)

        // then
        verify { view.updateSeatBackgroundColor(0, false) }
        verify { view.updateSelectResult(any()) }
    }

    @Test
    fun `영화를 예매하면 예매 알림을 등록한다`() {
        // given
        every { view.navigateToResultView(any()) } just runs
        every { view.setTicketAlarm(any()) } just runs

        // when
        presenter.reserveMovie(
            ticketRepository,
            reservation,
            selectedSeats,
        )

        // then
        verify { view.navigateToResultView(0L) }
        verify { view.setTicketAlarm(ticketRepository.find(0L)) }
    }
}
