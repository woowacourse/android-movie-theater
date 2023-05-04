package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.ReservationAdapterContract
import woowacourse.movie.data.repository.ReservationListRepository
import woowacourse.movie.domain.Price
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.domain.seat.Seats
import java.time.LocalDateTime

class ReservationAdapterTest {
    lateinit var reservationAdapterPresenter: ReservationAdapterContract.Presenter
    lateinit var view: ReservationAdapterContract.View
    private lateinit var reservationRepository: ReservationListRepository

    @Before
    fun init() {
        view = mockk()
        reservationRepository = mockk()
        reservationAdapterPresenter = ReservationAdapterPresenter(view, reservationRepository)
    }

    @Test
    fun setReservation() {
        // given
        every { view.setAdapterData(any()) } just runs
        every { reservationRepository.requestReservation() } returns listOf(
            fakeReservation()
        )

        // when
        reservationAdapterPresenter.setReservation()

        // then
        verify { view.setAdapterData(any()) }
    }

    private fun fakeReservation(): Reservation = Reservation(
        MovieMock.createMovie(),
        ReservationDetail(
            LocalDateTime.now(),
            1
        ),
        Seats(emptyList()),
        Price(),
    )
}
