package woowacourse.movie.movieDetail

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MovieModel
import woowacourse.movie.view.movieDetail.MovieDetailContract
import woowacourse.movie.view.movieDetail.MovieDetailPresenter
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenterTest {
    private lateinit var presenter: MovieDetailContract.Presenter
    private lateinit var view: MovieDetailContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(view)
    }

    @Test
    fun `초기 예매 인원 1에서 plus 를 하면 예매 인원이 1 증가한 2이다`() {
        // given
        val slot = slot<Int>()
        justRun { view.setPeopleCount(capture(slot)) }

        // when
        presenter.plusPeopleCount()

        // then
        val actual = slot.captured
        assertEquals(2, actual)
        verify { view.setPeopleCount(actual) }
    }

    @Test
    fun `초기 예매 인원 2에서 minus 를 하면 예매 인원이 1 감소한 1이다`() {
        // given
        val slot = slot<Int>()
        justRun { view.setPeopleCount(capture(slot)) }
        presenter.setPeopleCount(2)

        // when
        presenter.minusPeopleCount()

        // then
        val actual = slot.captured
        assertEquals(1, actual)
        verify { view.setPeopleCount(actual) }
    }

    @Test
    fun `상영일 목록은 상영 시작일과 상영 종료일까지의 목록이다`() {
        // given
        val slot = slot<List<LocalDate>>()
        justRun { view.setDateSpinner(capture(slot)) }

        // when 영화 상영일 5월 1일~5월10일일 때의 날짜 목록
        presenter.setScreeningDates(dummyMovie)

        // then
        val actual = slot.captured
        assertEquals(10, actual.size)
        verify { view.setDateSpinner(actual) }
    }

    @Test
    fun `평일 상영일로 변경하면 상영 시작 시간은 9시부터다`() {
        // given
        val weekday = LocalDate.of(2023, 5, 1)
        val slot = slot<List<LocalTime>>()
        justRun { view.setTimeSpinner(capture(slot)) }

        // when
        presenter.setScreeningTimes(weekday)

        // then
        val actual = slot.captured
        val expected = LocalTime.of(9, 0)
        assertEquals(expected, actual[0])
        verify { view.setTimeSpinner(actual) }
    }

    @Test
    fun `주말 상영일로 변경하면 상영 시작 시간은 10시부터다`() {
        // given
        val weekend = LocalDate.of(2023, 5, 6)
        val slot = slot<List<LocalTime>>()
        justRun { view.setTimeSpinner(capture(slot)) }

        // when
        presenter.setScreeningTimes(weekend)

        // then
        val actual = slot.captured
        val expected = LocalTime.of(10, 0)
        assertEquals(expected, actual[0])
        verify { view.setTimeSpinner(actual) }
    }

    companion object {
        private val dummyMovie = MovieModel(
            0,
            "그레이의 50가지 그림자 1",
            LocalDate.of(2023, 5, 1),
            LocalDate.of(2023, 5, 10),
            105,
            "모든 과거를 잊고 서로에게 더 깊게 빠져든 ‘크리스찬 그레이’와 ‘아나스타샤’. 그레이의 독특한 취향으로 시작된 이 비밀스러운 관계는 더 큰 자극을 원하는 아나스타샤로 인해 역전되고, 마침내 그녀의 본능이 지배하는 마지막 절정의 순간을 맞이하게 되는데…"
        )
    }
}
