package woowacourse.movie.presentation.activities.seatpicker.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.seat.DomainPickedSeats
import woowacourse.movie.presentation.activities.seatpicker.contract.SeatPickerContract
import woowacourse.movie.presentation.mapper.toDomain
import woowacourse.movie.presentation.mapper.toPresentation
import woowacourse.movie.presentation.model.MovieDate
import woowacourse.movie.presentation.model.MovieTime
import woowacourse.movie.presentation.model.Seat
import woowacourse.movie.presentation.model.SeatColumn
import woowacourse.movie.presentation.model.SeatRow
import woowacourse.movie.presentation.model.Ticket
import woowacourse.movie.presentation.model.TicketingState
import woowacourse.movie.presentation.model.movieitem.Movie
import java.time.LocalDate

class SeatPickerPresenterTest {
    private lateinit var presenter: SeatPickerContract.Presenter
    private lateinit var view: SeatPickerContract.View

    @Before
    internal fun setUp() {
        val movie = mockk<Movie>()
        every { movie.title } returns "영화 제목"
        every { movie.startDate } returns LocalDate.of(2023, 4, 1)
        every { movie.endDate } returns LocalDate.of(2023, 6, 1)
        every { movie.runningTime } returns 120
        every { movie.introduce } returns "영화 소개"

        val ticketingState = TicketingState(
            movie = movie,
            ticket = Ticket(1),
            movieDate = MovieDate(2023, 5, 5),
            movieTime = MovieTime(10, 10),
        )

        view = mockk(relaxUnitFun = true)
        presenter = SeatPickerPresenter(
            ticketingState = ticketingState,
            historyRepository = mockk(relaxed = true),
        )

        presenter.attach(view)
    }

    @Test
    internal fun 프레젠터에_View를_Attach할_때_View_초기화가_이루어진다() {
        // given
        /* ... */

        // when
        presenter.attach(view)

        // then
        verify(atLeast = 1) { view.showMovieTitle(any()) }
        verify(atLeast = 1) { view.updateTotalPriceView(any()) }
        verify(atLeast = 1) { view.updateReservationBtnEnabled(false) }
        verify(atLeast = 1) { view.initSeatTable(any(), any()) }
    }

    @Test
    internal fun 영화를_예매하면_알람을_등록하고_구매_결과_화면을_보여준다() {
        // given
        /* ... */

        // when
        presenter.reserveMovie()

        // then
        verify(exactly = 1) { view.registerPushBroadcast(any()) }
        verify(exactly = 1) { view.showTicketingResultScreen(any()) }
    }

    @Test
    internal fun 좌석이_이미_선택되어_있으면_선택을_해제한다() {
        // given
        val pickedSeats = mockk<DomainPickedSeats>()
        every { pickedSeats.seats } returns listOf(Seat(SeatRow('A'), SeatColumn(1)).toDomain())

        presenter.setState(pickedSeats.toPresentation())

        // when
        presenter.onClickSeat(Seat(SeatRow('A'), SeatColumn(1)))

        // then
        verify { view.updateReservationBtnEnabled(any()) }
        verify { view.setSeatViewPickState(any(), false) }
    }

    @Test
    internal fun 좌석이_이미_선택되어_있지_않으면_선택한다() {
        // given
        val pickedSeats = mockk<DomainPickedSeats>()
        every { pickedSeats.seats } returns listOf()

        presenter.setState(pickedSeats.toPresentation())

        // when
        presenter.onClickSeat(Seat(SeatRow('A'), SeatColumn(1)))

        // then
        verify { view.updateReservationBtnEnabled(any()) }
        verify { view.setSeatViewPickState(any(), true) }
    }
}
