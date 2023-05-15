package woowacourse.movie.view.activities.home.fragments.reservationlist

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

class ReservationListPresenterTest {

    private lateinit var view: ReservationListContract.View
    private lateinit var reservationRepository: ReservationRepository
    private val fakeReservation: Reservation = Reservation(
        Movie("title", Minute(152), "summary"),
        LocalDateTime.MAX,
        Theater("잠실", 5, 4),
        listOf(Point(1, 2), Point(2, 3))
    ).apply { this.id = 1L }
    private lateinit var sut: ReservationListPresenter

    @Before
    fun setUp() {
        view = mockk()
        reservationRepository = mockk()
        sut = ReservationListPresenter(view, reservationRepository)
    }

    @Test
    fun `예매 목록을 로드하면 뷰에 예매 목록 UI 상태를 설정한다`() {
        every { reservationRepository.findAll() } returns listOf(fakeReservation)
        every { view.setReservationList(listOf(fakeReservation).map(ReservationListViewItemUIState::from)) } just runs

        sut.loadReservations()

        verify { view.setReservationList(listOf(fakeReservation).map(ReservationListViewItemUIState::from)) }
    }
}