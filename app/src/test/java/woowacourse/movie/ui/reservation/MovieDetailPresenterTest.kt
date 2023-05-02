package woowacourse.movie.ui.reservation

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDate
import org.junit.Assert.assertEquals
import org.junit.Test
import woowacourse.movie.model.MovieState

class MovieDetailPresenterTest {
    @Test
    fun `표의 초기 값이 1이고 값을 추가하면 2가 된다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        val slot = slot<Int>()
        every { view.setCounterText(capture(slot)) } answers { println("slot : ${slot.captured}") }
        assertEquals(presenter.count.value, 1)

        // when
        presenter.plus()

        // then
        assertEquals(slot.captured, 2)
        verify { view.setCounterText(slot.captured) }
    }

    @Test
    fun `표는 1장 밑으로 떨어지지 않는다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        val slot = slot<Int>()
        every { view.setCounterText(capture(slot)) } answers { println("slot : ${slot.captured}") }
        assertEquals(presenter.count.value, 1)

        // when
        presenter.minus()

        // then
        assertEquals(slot.captured, 1)
        verify { view.setCounterText(slot.captured) }
    }

    @Test
    fun `영화 정보를 넘기면 상영 날짜를 반환한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        val movie = MovieState(
            imgId = 1,
            title = "타이틀",
            startDate = LocalDate.parse("2021-08-01"),
            endDate = LocalDate.parse("2021-08-03"),
            runningTime = 120,
            description = "설명"
        )

        // when
        val dates = presenter.getMovieRunningDates(movie)

        // then
        assertEquals(dates.size, 3)
        assertEquals(dates[0], LocalDate.parse("2021-08-01"))
        assertEquals(dates[1], LocalDate.parse("2021-08-02"))
        assertEquals(dates[2], LocalDate.parse("2021-08-03"))
    }

    @Test
    fun `상영 날짜를 넘기면 상영 시간을 반환한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        val date = LocalDate.parse("2021-08-01")

        // when
        val times = presenter.getMovieRunningTimes(date)

        // then
        assertEquals(times.size, 8)
    }
}
