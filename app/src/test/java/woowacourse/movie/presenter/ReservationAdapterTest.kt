package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.common.repository.ReservationRepository
import woowacourse.movie.domain.Price
import woowacourse.movie.domain.Reservation
import woowacourse.movie.domain.ReservationDetail
import woowacourse.movie.domain.mock.MovieMock
import woowacourse.movie.domain.seat.Seats
import woowacourse.movie.reservationList.ReservationAdapterContract
import woowacourse.movie.reservationList.ReservationAdapterPresenter
import java.time.LocalDateTime

class ReservationAdapterTest {
    private lateinit var reservationAdapterPresenter: ReservationAdapterContract.Presenter
    private lateinit var view: ReservationAdapterContract.View
    private lateinit var reservationRepository: ReservationRepository

    @Before
    fun init() {
        view = mockk()
        reservationRepository = mockk()
        reservationAdapterPresenter =
            ReservationAdapterPresenter(view, mockk(), reservationRepository)
    }

    @Test
    fun 예매_정보를_받아와_Adapter에_설정한다() {
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
            LocalDateTime.now(), 1
        ),
        Seats(emptyList()),
        Price(),
        ""
    )
}
