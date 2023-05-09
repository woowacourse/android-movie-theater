package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.common.mapper.ReservationMapper.toView
import woowacourse.movie.domain.Price
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.reservationResult.ReservationResultContract
import woowacourse.movie.reservationResult.ReservationResultPresenter
import java.time.LocalDateTime

class ReservationResultPresenterTest {
    private lateinit var reservationResultPresenter: ReservationResultContract.Presenter
    private lateinit var view: ReservationResultContract.View

    @Before
    fun init() {
        view = mockk()
    }

    @Test
    fun 예매_결과_화면을_세팅한다() {
        // given
        every { view.setMovieData(any()) } just runs
        every { view.setReservationDetailData(any()) } just runs
        every { view.setSeatData(any(), any(), any()) } just runs
        every { view.setPriceData(any()) } just runs
        val reservation = fakeReservation().toView()

        // when
        reservationResultPresenter = ReservationResultPresenter(view, reservation)

        // then
        verify { view.setMovieData(any()) }
        verify { view.setReservationDetailData(any()) }
        verify { view.setSeatData(any(), any(), any()) }
        verify { view.setPriceData(any()) }
    }

    private fun fakeReservation(): Reservation = Reservation(
        MovieMock.createMovie(),
        ReservationDetail(
            LocalDateTime.now(),
            1
        ),
        Seats(emptyList()),
        Price(),
        ""
    )
}
