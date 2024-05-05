package woowacourse.movie.presenter.reservation

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.R
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.screening.ScreeningDatabase
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.db.theater.TheaterDatabase
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningTimes
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
class ReservationDetailPresenterTest {
    @MockK
    private lateinit var view: ReservationDetailContract.View
    private lateinit var presenter: ReservationDetailContract.Presenter
    private lateinit var firstMovie: Movie
    private lateinit var firstTheater: Theater

    @BeforeEach
    fun setUp() {
        presenter = ReservationDetailPresenter(view, ScreeningDao(), TheaterDao())
        firstMovie = ScreeningDatabase.movies.first()
        firstTheater = TheaterDatabase.theaters.first()
    }

    @Test
    fun `영화와 극장 선택을 완료하면 선택한 영화 정보가 화면에 표시되어야 한다`() {
        val expectedMovieTitle = "해리 포터와 마법사의 돌"
        val expectedMoviePosterId = R.drawable.img_sorcerers_stone
        val expectedRunningTime = "152분"

        every { view.showMovieInformation(firstMovie) } answers {
            val actualMovie = arg<Movie>(0)
            assertEquals(expectedMoviePosterId, actualMovie.posterId)
            assertEquals(expectedMovieTitle, actualMovie.title)
            assertEquals(expectedRunningTime, actualMovie.runningTime)
        }
        presenter.loadMovie(0)
        verify { view.showMovieInformation(firstMovie) }
    }

    @Test
    fun `영화와 극장 선택을 완료하면 선택한 극장의 상영 날짜가 표시되어야 한다`() {
        val expectedPeriod =
            listOf(
                LocalDate.of(2024, 3, 1),
                LocalDate.of(2024, 3, 2),
                LocalDate.of(2024, 3, 3),
                LocalDate.of(2024, 3, 4),
                LocalDate.of(2024, 3, 5),
                LocalDate.of(2024, 3, 6),
                LocalDate.of(2024, 3, 7),
                LocalDate.of(2024, 3, 8),
                LocalDate.of(2024, 3, 9),
                LocalDate.of(2024, 3, 10),
            )
        every { view.showScreeningPeriod(firstMovie) } answers {
            val actualMovie = arg<Movie>(0)
            assertEquals(actualMovie.screeningPeriod, expectedPeriod)
        }
        presenter.loadScreeningPeriod(0)
        verify { view.showScreeningPeriod(firstMovie) }
    }

    @Test
    fun `영화와 극장 선택을 완료하면 선택한 극장의 상영 시간이 표시되어야 한다`() {
        val expectedScreeningTimes =
            ScreeningTimes(
                weekDay =
                    listOf(
                        LocalTime.of(9, 0),
                        LocalTime.of(11, 0),
                        LocalTime.of(13, 0),
                    ),
                weekEnd =
                    listOf(
                        LocalTime.of(14, 0),
                        LocalTime.of(16, 0),
                    ),
            )
        every {
            view.showScreeningTimes(
                firstTheater.screeningTimes,
                "2024-03-01",
            )
        }.answers {
            val actualScreeningTimes = arg<ScreeningTimes>(0)
            actualScreeningTimes.weekDay.forEachIndexed { index, localTime ->
                assertEquals(expectedScreeningTimes.weekDay[index], localTime)
            }
            actualScreeningTimes.weekEnd.forEachIndexed { index, localTime ->
                assertEquals(expectedScreeningTimes.weekEnd[index], localTime)
            }
        }
        presenter.loadScreeningTimes(0, "2024-03-01")
        verify {
            view.showScreeningTimes(
                firstTheater.screeningTimes,
                "2024-03-01",
            )
        }
    }

    @Test
    fun `예약 인원이 1인 상태에서 마이너스 버튼을 누르면 토스트를 띄워서 유저에게 알린다`() {
        every { view.showResultToast() } just runs
        presenter.decreaseHeadCount(1)
        verify { view.showResultToast() }
    }

    @Test
    fun `예약 인원이 2인 상태에 마이너스 버튼을 누르면 예약 인원은 1이 된다`() {
        val expectedCount = 1

        every { view.changeHeadCount(1) } answers {
            val actualCount = arg<Int>(0)
            assertEquals(actualCount, expectedCount)
        }
        presenter.decreaseHeadCount(2)
        verify { view.changeHeadCount(1) }
    }

    @Test
    fun `예약 인원이 20인 상태에서 플러스 버튼을 누르면 토스트를 띄어서 유저에게 알린다`() {
        every { view.showResultToast() } just runs
        presenter.increaseHeadCount(20)
        verify { view.showResultToast() }
    }

    @Test
    fun `예약 인원이 2인 상태에서 플러스 버튼을 누르면 예약 인원은 3이 된다`() {
        val expectedCount = 3

        every { view.changeHeadCount(3) } answers {
            val actualCount = arg<Int>(0)
            assertEquals(actualCount, expectedCount)
        }
        presenter.increaseHeadCount(2)
        verify { view.changeHeadCount(3) }
    }

    @Test
    fun `영화 정보가 존재하지 않는다면 토스트를 띄어서 유저에게 알린다`() {
        every { view.showIdErrorToast() } just runs
        presenter.checkIdValidation(-1, 0)
        verify { view.showIdErrorToast() }
    }

    @Test
    fun `극장 정보가 존재하지 않는다면 토스트를 띄어서 유저에게 알린다`() {
        every { view.showIdErrorToast() } just runs
        presenter.checkIdValidation(0, -1)
        verify { view.showIdErrorToast() }
    }
}
