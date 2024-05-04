package woowacourse.movie.feature.home.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.R
import woowacourse.movie.data.MovieRepository
import woowacourse.movie.model.Movie
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater
import java.time.LocalDate
import java.time.LocalTime

class MovieHomePresenterTest {
    private lateinit var view: MovieHomeContract.View
    private lateinit var presenter: MovieHomePresenter
    private val movies =
        listOf(
            Movie(
                id = 0,
                thumbnail = R.drawable.titanic,
                title = "타이타닉 0",
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
            ),
        )

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MovieHomePresenter(view)
        mockkObject(MovieRepository)
    }

    @Test
    fun `loadMovies를 호출하면 view에서 영화 리스트를 보여준다`() {
        // Given
        every { MovieRepository.getAllMovies() } returns movies
        every { view.displayMovies(any()) } just Runs

        // When
        presenter.loadMovies()

        // Then
        verify { view.displayMovies(movies) }
    }
}
