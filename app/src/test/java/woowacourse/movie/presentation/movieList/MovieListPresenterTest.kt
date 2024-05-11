package woowacourse.movie.presentation.movieList
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.model.movieInfo.MovieDate
import woowacourse.movie.model.movieInfo.MovieInfo
import woowacourse.movie.model.movieInfo.RunningTime
import woowacourse.movie.model.movieInfo.Synopsis
import woowacourse.movie.model.movieInfo.Title
import woowacourse.movie.model.theater.Seat
import woowacourse.movie.model.theater.Theater
import java.time.LocalDate

@ExtendWith(MockKExtension::class)
class MovieListPresenterTest {

    @RelaxedMockK
    private lateinit var view: MovieListView

    private var theaters: List<Theater> = generateTheaters(GENERATE_DUMMY_DATA_NUM)

    @InjectMockKs
    private lateinit var presenter: MovieListPresenter

    @Test
    fun `onDetailButtonClicked가 호출되었을때 view의 navigateToCinemaView로 theater 데이터가 전달되는지 검증`() {
        every { view.navigateToCinemaView(any()) } just runs

        presenter.onDetailButtonClicked(0)

        verify { view.navigateToCinemaView(theaters[0]) }
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
}
