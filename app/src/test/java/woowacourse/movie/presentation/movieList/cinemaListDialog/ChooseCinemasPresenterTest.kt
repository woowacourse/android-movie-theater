package woowacourse.movie.presentation.movieList.cinemaListDialog

import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.Cinema
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate
import java.time.LocalTime

@ExtendWith(MockKExtension::class)
class ChooseCinemasPresenterTest {
    @MockK
    private lateinit var view: ChooseCinemasContract.View

    @InjectMockKs
    private lateinit var presenter: ChooseCinemasPresenter

    private var theaters = generateTheaters(GENERATE_DUMMY_DATA_NUM)

    @Test
    fun `loadCinema 호출로 만들어진 데이터가 showCinema로 이동되는지 테스트`() {
        val expected = makeCinemasFromTheater(theaters[0])
        every { view.showCinemas(any()) } just runs

        presenter.loadCinema(theaters[0])

        verify { view.showCinemas(expected) }
    }

    @Test
    fun `selectedCinema로 넘어감 Cinma가 view의 navigateToMovieDetail로 이동되는지 테스트`() {
        val expected = makeCinemasFromTheater(theaters[0])[0]
        every { view.navigateToMovieDetail(any()) } just runs

        presenter.selectCinema(expected)

        verify { view.navigateToMovieDetail(expected) }
    }

    companion object {
        const val GENERATE_DUMMY_DATA_NUM = 10000

        private fun generateTheaters(count: Int): List<Theater> {
            return makeDummyData(count)
        }

        private fun makeDummyData(count: Int): List<Theater> {
            return (1..count).map { i ->
                val movie =
                    MovieInfo(
                        Title("차람과 하디의 진지한 여행기 $i"),
                        MovieDate(LocalDate.of(2024, 2, 25).plusDays(i.toLong())),
                        RunningTime(100 + i % 150),
                        Synopsis("Synopsis for movie $i"),
                    )
                val seats = generateSeats()
                Theater(movie, listOf(), seats)
            }
        }

        private fun generateSeats(): Map<String, Seat> {
            val seats = mutableMapOf<String, Seat>()
            val rows = 5
            val columns = 4

            for (row in 0 until rows) {
                for (col in 1..columns) {
                    val seatId = "${('A' + row)}$col"
                    val grade =
                        when (row) {
                            0, 1 -> "B"
                            2, 3 -> "S"
                            else -> "A"
                        }
                    seats[seatId] = Seat(('A' + row), col, grade)
                }
            }
            return seats
        }
    }

    fun makeCinemasFromTheater(theater: Theater): List<Cinema> {
        return listOf(
            Cinema(
                "CGV",
                theater.copy(
                    times =
                        listOf(
                            LocalTime.of(9, 0),
                            LocalTime.of(11, 0),
                            LocalTime.of(15, 0),
                        ),
                ),
            ),
            Cinema(
                "롯데시네마",
                theater.copy(
                    times =
                        listOf(
                            LocalTime.of(13, 0),
                            LocalTime.of(15, 0),
                            LocalTime.of(17, 0),
                            LocalTime.of(19, 0),
                        ),
                ),
            ),
            Cinema(
                "메가 박스",
                theater.copy(
                    times =
                        listOf(
                            LocalTime.of(20, 0),
                            LocalTime.of(22, 0),
                            LocalTime.of(23, 30),
                        ),
                ),
            ),
        )
    }
}
