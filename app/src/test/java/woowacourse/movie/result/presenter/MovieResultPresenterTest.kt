package woowacourse.movie.result.presenter

import android.content.Context
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkObject
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.MovieApplication
import woowacourse.MovieApplication.Companion.database
import woowacourse.movie.R
import woowacourse.movie.data.db.ReservationHistoryEntity
import woowacourse.movie.data.repository.HomeContentRepository
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.home.view.adapter.movie.HomeContent
import woowacourse.movie.model.MovieDate
import woowacourse.movie.model.Theater
import woowacourse.movie.result.presenter.contract.MovieResultContract
import java.time.LocalDate
import java.time.LocalTime

class MovieResultPresenterTest {
    private lateinit var view: MovieResultContract.View
    private lateinit var presenter: MovieResultPresenter
    private lateinit var context: Context

    private val movie =
        HomeContent.Movie(
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
        presenter = MovieResultPresenter(view)
        context = mockk()
        mockkObject(HomeContentRepository)
        mockkObject(MovieApplication.Companion)
    }

    @Test
    fun `loadMovieTicket를 호출하면 예매한 영화 티켓의 정보가 보여진다`() {
        // Given
        every { view.displayMovieTicket(any()) } just runs
        every { getMovieById(any()) } returns movie
        every {
            database.reservationHistoryDao().findReservationHistoryById(any())
        } returns ReservationHistoryEntity("2024-05-05", "13:00", 1, "A1", 0L, 1)

        // When
        presenter.loadMovieTicket(context, 0L)

        // Then
        verify { view.displayMovieTicket(any()) }
    }
}
