package woowacourse.movie.feature.reservation

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.feature.reservation.ReservationDetailActivity.Companion.DEFAULT_MOVIE_ID

@ExtendWith(MockKExtension::class)
class ReservationDetailPresenterTest {
    @MockK
    private lateinit var view: ReservationDetailContract.View
    private lateinit var presenter: ReservationDetailContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = ReservationDetailPresenter(view, ScreeningDao(), TheaterDao(), DEFAULT_MOVIE_ID)
    }

    @Test
    fun `영화 정보를 보여준다`() {
        every { view.showMovieInformation(any()) } just runs
        presenter.loadMovie()
        verify { view.showMovieInformation(any()) }
    }

    @Test
    fun `상영 기간을 보여준다`() {
        every { view.showScreeningPeriod(any()) } just runs
        presenter.loadScreeningPeriod()
        verify { view.showScreeningPeriod(any()) }
    }

    @Test
    fun `상영 시간을 보여준다`() {
        every { view.showScreeningTimes(any(), any()) } just runs
        presenter.loadScreeningTimes(0, "2024-03-01")
        verify { view.showScreeningTimes(any(), any()) }
    }

    @Test
    fun `예약 인원이 1인 상태에서 마이너스 버튼을 누르면 토스트를 보여준다`() {
        every { view.showResultToast() } just runs
        presenter.decreaseHeadCount()
        verify { view.showResultToast() }
    }

    @Test
    fun `예약 인원이 2인 상태에 마이너스 버튼을 누르면 예약 인원은 1이 된다`() {
        every { view.changeHeadCount(any()) } just runs
        presenter.increaseHeadCount()
        presenter.decreaseHeadCount()
        verify { view.changeHeadCount(1) }
    }

    @Test
    fun `예약 인원이 20인 상태에서 플러스 버튼을 누르면 토스트를 보여준다`() {
        every { view.changeHeadCount(any()) } just runs
        every { view.showResultToast() } just runs
        repeat(20) {
            presenter.increaseHeadCount()
        }
        verify { view.showResultToast() }
    }

    @Test
    fun `예약 인원이 2인 상태에서 플러스 버튼을 누르면 예약 인원은 3이 된다`() {
        every { view.changeHeadCount(any()) } just runs
        repeat(2) {
            presenter.increaseHeadCount()
        }
        verify { view.changeHeadCount(3) }
    }
}
