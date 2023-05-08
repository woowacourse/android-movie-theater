package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.common.mapper.MovieMapper.toView
import woowacourse.movie.common.mapper.ReservationDetailMapper.toView
import woowacourse.movie.common.mapper.SeatsMapper.toView
import woowacourse.movie.common.repository.ReservationRepository
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.seatSelection.SeatSelectionContract
import woowacourse.movie.seatSelection.SeatSelectionPresenter
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
    fun 좌석_선택_화면을_설정한다() {
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
    fun 좌석을_선택하면_가격과_예매_버튼을_설정한다() {
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
    fun 예매_버튼을_누르면_알람을_설정하고_예매_결과_액티비티를_시작한다() {
        // given
        every { view.makeReservationAlarm(any(), any()) } just runs
        every { view.startReservationResultActivity(any()) } just runs
        every { reservationRepository.postReservation(any()) } just runs
        // when
        val movie = fakeMovie().toView()
        val reservationDetail = fakeReservationDetail().toView()
        val seats = fakeSeats().toView()
        val theaterName = ""
        seatSelectionPresenter.confirmSeats(movie, reservationDetail, seats, theaterName)

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
