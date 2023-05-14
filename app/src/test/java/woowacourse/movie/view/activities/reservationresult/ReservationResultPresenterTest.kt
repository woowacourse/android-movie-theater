package woowacourse.movie.view.activities.reservationresult

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.screening.Minute
import woowacourse.movie.domain.screening.Movie
import woowacourse.movie.domain.screening.Reservation
import woowacourse.movie.domain.theater.Point
import woowacourse.movie.domain.theater.Theater
import woowacourse.movie.repository.ReservationRepository
import java.time.LocalDateTime

class ReservationResultPresenterTest {

    private lateinit var view: ReservationResultContract.View
    private lateinit var reservationRepository: ReservationRepository
    private val fakeReservation: Reservation = Reservation(
        Movie("title", Minute(152), "summary"),
        LocalDateTime.MAX,
        Theater(5, 4),
        listOf(Point(1, 2), Point(2, 3))
    )

    @Before
    fun setUp() {
        view = mockk()
        reservationRepository = mockk()
    }

    @Test
    fun `예매를 로드하면 뷰에 예매 UI 상태를 설정한다`() {
        val reservationId = 1L
        every { reservationRepository.findById(reservationId) } returns fakeReservation
        every { view.setReservation(any()) } just runs
        val sut = ReservationResultPresenter(view, reservationId, reservationRepository)

        sut.loadReservation()

        verify { view.setReservation(any()) }
    }
}
