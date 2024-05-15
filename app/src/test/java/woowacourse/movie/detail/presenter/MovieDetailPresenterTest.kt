package woowacourse.movie.detail.presenter

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import io.mockk.verifyAll
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.data.repository.HomeContentRepository
import woowacourse.movie.detail.presenter.contract.MovieDetailContract
import woowacourse.movie.home.view.adapter.movie.HomeContent.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenterTest {
    private lateinit var view: MovieDetailContract.View
    private lateinit var presenter: MovieDetailPresenter
    private val movie =
        Movie(
            id = 0,
            image = R.drawable.titanic,
            title = "타이타닉 1",
            description =
                "우연한 기회로 티켓을 구해 타이타닉호에 올라탄 자유로운 영혼을 가진 화가 ‘잭’(레오나르도 디카프리오)은 막강한 재력의 약혼자와 함께 1등실에 승선한 ‘로즈’(케이트 윈슬렛)에게 한눈에 반한다. " +
                    "진실한 사랑을 꿈꾸던 ‘로즈’ 또한 생애 처음 황홀한 감정에 휩싸이고, 둘은 운명 같은 사랑에 빠지는데… 가장 차가운 곳에서 피어난 뜨거운 사랑! 영원히 가라앉지 않는 세기의 사랑이 펼쳐진다!",
            date = MovieDate(LocalDate.of(2024, 4, 1), LocalDate.of(2024, 4, 28)),
            runningTime = 152,
            theaters =
                listOf(
                    Theater(
                        "선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉선릉",
                        listOf(LocalTime.of(10, 0), LocalTime.of(14, 0), LocalTime.of(18, 0)),
                    ),
                    Theater(
                        "잠실",
                        listOf(
                            LocalTime.of(12, 30),
                            LocalTime.of(13, 30),
                            LocalTime.of(15, 0),
                            LocalTime.of(17, 0),
                            LocalTime.of(21, 0),
                        ),
                    ),
                    Theater(
                        "강남",
                        listOf(LocalTime.of(17, 0), LocalTime.of(20, 0)),
                    ),
                ),
        )

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(view)
        mockkObject(HomeContentRepository)
    }

    @Test
    fun `loadMovieDetail를 호출하면 view에서 영화 정보를 보여주고 spinner의 정보가 세팅된다`() {
        // Given
        val movieSlot = slot<Movie>()
        val dateSlot = slot<MovieDate>()
        val timeSlot = mutableListOf<List<LocalTime>>()

        every { HomeContentRepository.getMovieById(any()) } returns movie
        every { view.displayMovieDetail(capture(movieSlot), any()) } just Runs
        every { view.setUpDateSpinner(capture(dateSlot)) } just Runs
        every { view.setUpTimeSpinner(capture(timeSlot)) } just Runs

        // When
        presenter.loadMovieDetail(0L, 0)

        // Then
        verifyAll {
            view.displayMovieDetail(movie, any())
            view.setUpDateSpinner(movie.date)
            view.setUpTimeSpinner(any())
        }

        assert(movieSlot.captured == movie)
        assert(dateSlot.captured == movie.date)
        assert(timeSlot.all { it == movie.theaters[0].screeningTimes })
    }

    @Test
    fun `plusReservationCount를 호출하면 카운트가 1만큼 증가한다`() {
        // Given
        every { view.updateCountView(any()) } just runs

        // When
        presenter.plusReservationCount()

        // Then
        verify { view.updateCountView(2) }
    }

    @Test
    fun `count가 1일때 minusReservationCount를 호출하면 카운트가 감소되지 않는다`() {
        // Given
        every { view.updateCountView(any()) } just runs

        // When
        presenter.minusReservationCount()

        // Then
        verify { view.updateCountView(1) }
    }

    @Test
    fun `minusReservationCount를 호출하면 카운트가 감소된다`() {
        // Given
        every { view.updateCountView(any()) } just runs

        // When
        presenter.plusReservationCount()
        presenter.plusReservationCount()
        presenter.minusReservationCount()

        // Then
        verify { view.updateCountView(2) }
    }

    @Test
    fun `reserveMovie를 호출하면 SeatSelectionView로 이동한다`() {
        // Given
        every { view.navigateToSeatSelectionView(any(), any(), any(), any()) } just runs

        // When
        presenter.reserveMovie(0, "2024-04-01", "10:00")

        // Then
        verify { view.navigateToSeatSelectionView(0, "2024-04-01", "10:00", any()) }
    }
}
