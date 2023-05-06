package woowacourse.movie.ui.reservation

import android.net.Uri
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
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
        presenter.onPlusClick()

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
        presenter.onMinusClick()

        // then
        assertEquals(slot.captured, 1)
        verify { view.setCounterText(slot.captured) }
    }

    @Test
    fun `영화 정보를 넘기면 상영 날짜를 반환한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        presenter.init("cinemaName", sampleMovieState)

        // when
        val dates = presenter.getMovieRunningDates()

        // then
        assertEquals(dates.size, 4)
        assertEquals(dates[0], LocalDate.parse("-999999999-01-01"))
        assertEquals(dates[1], LocalDate.parse("-999999999-01-02"))
        assertEquals(dates[2], LocalDate.parse("-999999999-01-03"))
    }

    @Test
    fun `상영 날짜를 넘기면 상영 시간을 반환한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        presenter.init("cinemaName", sampleMovieState)

        // when
        val times = presenter.getMovieRunningTimes()

        // then
        assertEquals(times.size, 2)
    }

    @Test
    fun `예매하기 버튼을 누르면 좌석 선택 화면으로 이동한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        presenter.init("cinemaName", sampleMovieState)

        // when
        presenter.onReserveButtonClick()

        // then
        verify { view.navigateSeatSelectActivity(sampleMovieState, "cinemaName") }
    }

    @Test
    fun `영화 정보를 넘기면 영화 정보를 바인딩한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view)
        presenter.init("cinemaName", sampleMovieState)

        // when
        presenter.getMovieRunningTimes()

        // then
        verify { view.setMovie(sampleMovieState) }
    }

    companion object {
        val sampleMovieState = MovieState(
            Uri.parse(""),
            "title",
            LocalDate.MIN,
            LocalDate.MIN.plusDays(3),
            listOf(LocalTime.parse("10:00"), LocalTime.parse("12:00")),
            152,
            "description"
        )
    }
}
