package woowacourse.movie.presentation.view.reservation.detail

import io.mockk.every
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.presentation.repository.MovieRepository

@ExtendWith(MockKExtension::class)
class MovieDetailPresenterImplTest {
    private lateinit var view: MovieDetailContract.View
    private lateinit var presenter: MovieDetailPresenterImpl
    private lateinit var movieRepository: MovieRepository

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        movieRepository = mockk(relaxed = true)

        presenter = MovieDetailPresenterImpl(0, 0, movieRepository)
        presenter.attachView(view)
    }

    @Test
    fun `view를 만들면 영화 상세 정보를 출력한다`() {
        // when
        every { movieRepository.getScreeningDateInfo(any()) } returns listOf()
        every { movieRepository.getScreeningTimeInfo(any()) } returns listOf()
        every { presenter.loadScreeningDates(any()) } just runs
        every { presenter.loadScreeningTimes(any()) } just runs

        // when
        presenter.onViewSetUp()

        // then
        verify { view.setScreeningDatesAndTimes(any(), any(), any()) }
    }

    @Test
    fun `상영 날짜를 선택하면 해당 날짜에 맞는 시간을 설정한다`() {
        // given
        every { presenter.loadScreeningTimes(any()) } just runs

        // when
        presenter.selectDate("2024-04-01")

        // then
        verify(exactly = 1) { view.updateScreeningTimes(any(), any()) }
    }

    @Test
    fun `예매 버튼을 누르면 count값을 가지고 좌석 선택 화면으로 넘어간다`() {
        // given
        val count = 1

        // when
        presenter.onReserveButtonClicked()

        // then
        verify(exactly = 1) { view.moveToSeatSelection(count, any()) }
    }
}
