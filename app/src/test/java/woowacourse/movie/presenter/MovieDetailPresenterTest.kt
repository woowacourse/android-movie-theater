package woowacourse.movie.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.presentation.repository.MovieRepository
import woowacourse.movie.presentation.repository.ReservationMovieInfoRepository
import woowacourse.movie.presentation.repository.TheaterRepository
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailContract
import woowacourse.movie.presentation.view.reservation.detail.MovieDetailPresenterImpl
import woowacourse.movie.repository.TheaterRepositoryImpl

class MovieDetailPresenterTest {
    private lateinit var view: MovieDetailContract.View
    private lateinit var presenter: MovieDetailPresenterImpl
    private lateinit var movieRepository: MovieRepository
    private lateinit var theaterRepository: TheaterRepositoryImpl
    private lateinit var reservationMovieInfoRepository: ReservationMovieInfoRepository

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        movieRepository = mockk(relaxed = true)
        theaterRepository = mockk(relaxed = true)
        reservationMovieInfoRepository = mockk(relaxed = true)

        presenter = MovieDetailPresenterImpl(
            0,
            0,
            movieRepository,
            theaterRepository,
            reservationMovieInfoRepository,
        )
        presenter.attachView(view)
    }

    @Test
    fun `View가 만들어지면 영화 상세 정보 출력을 요청한다`() {
        // when
        presenter.onViewSetUp()

        // then
        verify { view.showMovieDetails(any()) }
    }

    @Test
    fun `View가 만들어지면 상영 날짜와 시간 스피너 설정을 요청한다`() {
        every { theaterRepository.getScreeningDateInfo(any(), any()) } returns listOf()
        every { theaterRepository.getScreeningTimeInfo(any(), any(), any()) } returns listOf()
        every { presenter.loadScreeningDates() } just runs
        every { presenter.loadScreeningTimes(any()) } just runs

        // when
        presenter.onViewSetUp()

        // then
        verify { view.setScreeningDates(any(), any()) }
    }

    @Test
    fun `날짜를 선택하면 새로운 시간 스피너 설정을 요청한다`() {
        // when
        presenter.selectDate("2024-04-06")

        every { presenter.loadScreeningTimes(any()) } just runs

        // then
        verify { view.updateScreeningTimes(any(), any()) }
    }

    @Test
    fun `예매 인원 감소 버튼을 누르면 view에게 변경된 예매 수를 보여줄 것을 요청한다`() {
        // given
        presenter.plusReservationCount()

        // when
        presenter.minusReservationCount()

        // then
        verify { view.updateReservationCount(1) }
    }

    @Test
    fun `예매 인원 증가 버튼을 누르면 view에게 변경된 예매 수를 보여줄 것을 요청한다`() {
        // when
        presenter.plusReservationCount()

        // then
        verify { view.updateReservationCount(2) }
    }

    @Test
    fun `예매 버튼을 누르면 view에게 좌석 선택 화면으로 넘어갈 것을 요청한다`() {
        // when
        presenter.onReserveButtonClicked()

        // then
        verify { view.moveToSeatSelection(1, any()) }
    }
}
