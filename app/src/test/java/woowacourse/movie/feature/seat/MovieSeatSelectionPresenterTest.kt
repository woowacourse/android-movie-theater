package woowacourse.movie.feature.seat

import android.content.Context
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.movie.MovieRepositoryImpl
import woowacourse.movie.data.notification.NotificationRepository
import woowacourse.movie.data.reservation.ReservationRepository
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
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
import woowacourse.movie.model.notification.TicketAlarm

class MovieSeatSelectionPresenterTest {
    private lateinit var view: MovieSeatSelectionContract.View
    private lateinit var applicationContext: Context
    private lateinit var notificationRepository: NotificationRepository
    private lateinit var ticketAlarm: TicketAlarm
    private lateinit var presenter: MovieSeatSelectionContract.Presenter
    private lateinit var ticketRepository: TicketRepository
    private lateinit var reservationRepository: ReservationRepository

    @BeforeEach
    fun setUp() {
        view = mockk()
        applicationContext = mockk()
        notificationRepository = mockk()
        ticketAlarm = mockk()
        ticketRepository = FakeTicketRepository()
        reservationRepository = ReservationRepositoryImpl

        presenter =
            MovieSeatSelectionPresenter(
                view,
                applicationContext,
                notificationRepository,
                ticketAlarm,
            )
        presenter.updateSelectedSeats(selectedSeats)
    }

    @Test
    fun `예매 정보 id에 맞는 예매 정보를 불러온다`() {
        // given
        val reservationSlot = slot<Reservation>()
        every { view.setUpReservation(capture(reservationSlot), any()) } just runs
        val reservationId =
            reservationRepository.save(
                movieId,
                screeningDate,
                screeningTime,
                reservationCount,
                theaterName,
            )

        // when
        presenter.loadReservation(reservationId)

        // then
        val reservation = reservationSlot.captured
        val movie = MovieRepositoryImpl.getMovieById(reservation.movieId)
        assertThat(reservation.id).isEqualTo(reservationId)
        verify { view.setUpReservation(reservation, movie) }
    }

    @Test
    fun `존재하지 않는 예매 정보 id의 경우 예외가 발생한다`() {
        // given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // when
        presenter.loadReservation(-1L)

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
    fun `알림 수신이 켜져있는 경우 영화를 예매하면 예매 알림을 등록한다`() {
        // given
        every { view.navigateToResultView(any()) } just runs
        every { notificationRepository.isGrant() } returns true
        every { ticketAlarm.setReservationAlarm(any()) } just runs

        // when
        presenter.reserveMovie(
            ticketRepository,
            reservation,
            selectedSeats,
        )

        // then
        verify { view.navigateToResultView(0L) }
        every { ticketAlarm.setReservationAlarm(any()) }
    }

    @Test
    fun `알림 수신이 꺼져있는 경우 영화를 예매해도 예매 알림을 등록하지 않는다`() {
        // given
        every { view.navigateToResultView(any()) } just runs
        every { notificationRepository.isGrant() } returns false

        // when
        presenter.reserveMovie(
            ticketRepository,
            reservation,
            selectedSeats,
        )

        // then
        verify { view.navigateToResultView(0L) }
        verify(exactly = 0) { ticketAlarm.setReservationAlarm(any()) }
    }
}
