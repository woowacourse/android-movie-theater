package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.SeatSelectionContract
import woowacourse.movie.data.repository.ReservationRepository
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.mapper.MovieMapper.toView
import woowacourse.movie.mapper.ReservationDetailMapper.toView
import woowacourse.movie.mapper.SeatsMapper.toView
import java.time.LocalDateTime

class SeatSelectionPresenterTest {
    lateinit var seatSelectionPresenter: SeatSelectionContract.Presenter
    lateinit var view: SeatSelectionContract.View
    lateinit var reservationRepository: ReservationRepository

    @Before
    fun init() {
        view = mockk()
        reservationRepository = mockk()
        seatSelectionPresenter = SeatSelectionPresenter(view)
    }

    @Test
    fun initActivity() {
        // given
        every { view.makeSeatLayout(any(), any()) } just runs
        every { view.setMovieData(any()) } just runs
        every { view.setPriceText(any()) } just runs

        // when
        val movie = fakeMovie().toView()
        val reservationDetail = fakeReservationDetail().toView()
        seatSelectionPresenter.initActivity(movie, reservationDetail)

        // then
        verify { view.makeSeatLayout(any(), any()) }
        verify { view.setMovieData(any()) }
        verify { view.setPriceText(any()) }
    }

    @Test
    fun selectSeat() {
        // given
        every { view.setReservationButtonState(any(), any()) } just runs
        every { view.setPriceText(any()) } just runs

        // when
        val seats = fakeSeats().toView()
        val reservationDetail = fakeReservationDetail().toView()
        seatSelectionPresenter.selectSeat(seats, reservationDetail)

        // then
        verify { view.setReservationButtonState(any(), any()) }
        verify { view.setPriceText(any()) }
    }

    @Test
    fun confirmSeats() {
        // given
        every { view.makeReservationAlarm(any(), any()) } just runs
        every { view.startReservationResultActivity(any()) } just runs
        every { reservationRepository.postReservation(any()) } just runs
        // when
        val movie = fakeMovie().toView()
        val reservationDetail = fakeReservationDetail().toView()
        val seats = fakeSeats().toView()
        seatSelectionPresenter.confirmSeats(movie, reservationDetail, seats)

        // then
        verify { view.makeReservationAlarm(any(), any()) }
        verify { view.startReservationResultActivity(any()) }
    }

    private fun fakeSeats(): Seats = Seats(emptyList())

    private fun fakeMovie(): Movie = MovieMock.createMovie()

    private fun fakeReservationDetail(): ReservationDetail = ReservationDetail(
        LocalDateTime.now(),
        1
    )
}
