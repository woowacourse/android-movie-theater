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
import woowacourse.movie.TestFixture.getMockScreeningTimes
import woowacourse.movie.TestFixture.mockMovies
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.theater.TheaterDao

@ExtendWith(MockKExtension::class)
class ReservationPresenterTest {
    @MockK
    private lateinit var view: ReservationContract.View
    private lateinit var presenter: ReservationContract.Presenter

    @BeforeEach
    fun setUp() {
        every { view.changeHeadCount(1) } just runs
        presenter =
            ReservationPresenter(
                view,
                ScreeningDao(),
                TheaterDao(),
                movieId = 0,
                theaterId = 0,
                savedHeadCount = 1,
            )
        verify { view.changeHeadCount(1) }
    }

    @Test
    fun `영화 정보를 보여준다`() {
        every { view.showMovieInformation(any()) } just runs
        presenter.loadMovie()
        verify { view.showMovieInformation(mockMovies[0]) }
    }

    @Test
    fun `상영 기간을 불러온다`() {
        every { view.showScreeningDates(any()) } just runs
        presenter.loadScreeningDates()
        verify { view.showScreeningDates(mockMovies[0].screeningPeriod) }
    }

    @Test
    fun `상영 시간을 불러온다`() {
        every { view.showScreeningTimes(any()) } just runs
        presenter.loadScreeningTimes()
        val mockScreeningTimes = getMockScreeningTimes(movieId = 0, theaterId = 0)
        verify { view.showScreeningTimes(mockScreeningTimes) }
    }

    @Test
    fun `예약 인원이 1인 상태에서 마이너스 버튼을 누르면 토스트를 보여준다`() {
        every { view.showResultToast() } just runs
        presenter.decreaseHeadCount()
        verify { view.showResultToast() }
    }

    @Test
    fun `예약 인원이 2인 상태에 마이너스 버튼을 누르면 예약 인원은 1이 된다`() {
        every { view.changeHeadCount(2) } just runs
        presenter.increaseHeadCount()
        verify { view.changeHeadCount(2) }

        every { view.changeHeadCount(1) } just runs
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

    @Test
    fun `선택된 상영시간을 보여준다`() {
        every { view.showScreeningTime(0) } just runs
        presenter.selectScreeningTime(0)
        verify { view.showScreeningTime(0) }
    }
}
