package woowacourse.movie.presenter.reservation

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyScreening
import woowacourse.movie.domain.model.Cinema
import woowacourse.movie.domain.model.ReservationCount
import woowacourse.movie.domain.model.ReservationInfo
import woowacourse.movie.view.reservation.ReservationContract
import woowacourse.movie.view.reservation.ReservationPresenter
import java.time.LocalDateTime

class ReservationPresenterTest {
    private lateinit var presenter: ReservationPresenter
    private lateinit var view: ReservationContract.View

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = ReservationPresenter(view)
    }

    @Test
    fun `영화 정보를 불러온다`() {
        // given
        every { view.updateReservationCount(any()) } just Runs
        every { view.showMovieDetail(any()) } just Runs
        // when
        presenter.loadData(DummyScreening.dummyScreenings[0])
        // then
        verify { view.showMovieDetail(DummyScreening.dummyScreenings[0]) }
    }

    @Test
    fun `예약 인원이 증가한다`() {
        // given
        every { view.updateReservationCount(any()) } just Runs
        // when
        presenter.increaseCount(2)
        // then
        verify { view.updateReservationCount(3) }
    }

    @Test
    fun `예약 인원이 감소한다`() {
        // given
        every { view.showMovieDetail(any()) } just Runs
        every { view.updateReservationCount(any()) } just Runs
        // when
        presenter.loadData(DummyScreening.dummyScreenings[0], 3)
        presenter.decreaseCount(1)
        // then
        verify { view.updateReservationCount(2) }
    }

    @Test
    fun `날짜를 선택하면 해당 날짜의 선택 가능한 시간 목록을 보여준다`() {
        val now = LocalDateTime.of(2025, 4, 1, 0, 0)
        val times = DummyScreening.dummyScreenings[0].availableTimes(now, now.toLocalDate())
        // given
        every { view.showMovieDetail(any()) } just Runs
        every { view.updateReservationCount(any()) } just Runs
        every { view.updateDateSet(any()) } just Runs
        every { view.updateTimeSet(any()) } just Runs

        // when
        presenter.loadData(DummyScreening.dummyScreenings[0])
        presenter.selectDate(now.toLocalDate())

        // then
        verify { view.updateTimeSet(times) }
    }

    @Test
    fun `예약 시 조건이 맞으면 예약 정보를 넘긴다`() {
        val now = LocalDateTime.of(2025, 5, 29, 11, 0, 0)
        // given
        every { view.showMovieDetail(any()) } just Runs
        every { view.updateReservationCount(any()) } just Runs
        every { view.navigateToSeatSelectionScreen(any()) } just Runs

        // when
        presenter.loadData(DummyScreening.dummyScreenings[0], 3)
        presenter.onReserve(now.toLocalDate(), now.toLocalTime())
        // then
        verify {
            view.navigateToSeatSelectionScreen(
                ReservationInfo(
                    title = "해리 포터와 마법사의 돌",
                    reservationDateTime = LocalDateTime.of(now.toLocalDate(), now.toLocalTime()),
                    reservationCount = ReservationCount(3),
                    cinema = Cinema(1, "선릉"),
                ),
            )
        }
    }
}
