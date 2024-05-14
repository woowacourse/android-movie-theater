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
import woowacourse.movie.data.notification.FakeNotificationRepository
import woowacourse.movie.data.reservation.ReservationRepositoryImpl
import woowacourse.movie.data.reservation.dto.Reservation
import woowacourse.movie.data.ticket.FakeTicketRepository
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
    private lateinit var ticketAlarm: TicketAlarm
    private lateinit var presenter: MovieSeatSelectionContract.Presenter
    private val notificationRepository = FakeNotificationRepository()
    private val ticketRepository = FakeTicketRepository()
    private val reservationRepository = ReservationRepositoryImpl

    @BeforeEach
    fun setUp() {
        view = mockk()
        applicationContext = mockk()
        ticketAlarm = mockk()

        presenter =
            MovieSeatSelectionPresenter(
                view,
                applicationContext,
                notificationRepository,
                ticketAlarm,
                ticketRepository,
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
        val movie = MovieRepositoryImpl.find(reservation.movieId)
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
        every { ticketAlarm.setReservationAlarm(any()) } just runs
        notificationRepository.update(true)

        // when
        presenter.reserveMovie(
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
        notificationRepository.update(false)

        // when
        presenter.reserveMovie(
            reservation,
            selectedSeats,
        )

        // then
        verify { view.navigateToResultView(0L) }
        verify(exactly = 0) { ticketAlarm.setReservationAlarm(any()) }
    }
}
