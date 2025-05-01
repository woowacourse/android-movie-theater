package woowacourse.movie.presenter.theater

import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.presenter.THEATER_MOVIE_SCHEDULES
import woowacourse.movie.presenter.THEATER_MOVIE_SCHEDULE_LOTTE

class TheaterPresenterTest {
    private lateinit var presenter: TheaterPresenter
    private lateinit var view: TheaterContracts.View

    @BeforeEach
    fun setup() {
        view = mockk()
        presenter = TheaterPresenter(view)
    }

    @Test
    fun `영화관 상영정보를 업데이트하면 영화관 상영정보 뷰가 갱신된다`() {
        // given
        every { view.showTheaterMovieSchedule(any()) } just Runs

        // when
        presenter.updateTheaterMovieSchedules(THEATER_MOVIE_SCHEDULES)

        // then
        verify {
            view.showTheaterMovieSchedule(any())
        }
    }

    @Test
    fun `예매 요청이 들어오면 예매 화면 뷰가 보인다`() {
        // given
        every { view.showReservationView(any()) } just Runs

        // when
        presenter.onReservationRequested(THEATER_MOVIE_SCHEDULE_LOTTE)

        // then
        verify {
            view.showReservationView(any())
        }
    }

    @AfterEach
    fun finish() {
        clearAllMocks()
    }
}
