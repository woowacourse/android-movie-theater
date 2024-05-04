package woowacourse.movie.seatselection.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.data.MovieRepository.getMovieById
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater
import woowacourse.movie.seatselection.presenter.contract.MovieSeatSelectionContract
import java.time.LocalDate
import java.time.LocalTime

class MovieSeatSelectionPresenterTest {
    private lateinit var view: MovieSeatSelectionContract.View
    private lateinit var presenter: MovieSeatSelectionPresenter

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
                        "선릉",
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
        presenter = MovieSeatSelectionPresenter(view)
        presenter.updateSelectedSeats(3)
        mockkObject(MovieRepository)
    }

    @Test
    fun `loadMovieTitle를 호출하면 선택된 영화의 제목이 보여진다`() {
        // Given
        every { getMovieById(any()) } returns movie
        every { view.displayMovieTitle(any()) } just runs
        every { view.updateSelectedSeats(any()) } just runs

        // When
        presenter.loadDetailMovie(0L)

        // Then
        verify { view.displayMovieTitle("타이타닉 1") }
    }

    @Test
    fun `loadTableSeats를 호출하면 테이블 좌석들의 정보가 세팅된다`() {
        // Given
        every { view.setUpTableSeats(any()) } just runs

        // When
        presenter.loadTableSeats(3)

        // Then
        verify { view.setUpTableSeats(any()) }
    }

    @Test
    fun `clickTableSeat를 호출하면 좌석의 배경색이 바뀌고 선택 결과가 화면에 보여진다`() {
        // Given
        every { view.updateSeatBackgroundColor(any(), any()) } just runs
        every { view.updateSelectedSeats(any()) } just runs

        // When
        presenter.clickTableSeat(0)

        // Then
        verify { view.updateSeatBackgroundColor(0, false) }
        verify { view.updateSelectedSeats(any()) }
    }

    @Test
    fun `clickPositiveButton를 예매 결과를 보여주는 화면으로 이동한다`() {
        // Given
        every { view.navigateToResultView(any()) } just runs

        // When

        presenter.clickPositiveButton()

        // Then
        verify { view.navigateToResultView(any()) }
    }
}
