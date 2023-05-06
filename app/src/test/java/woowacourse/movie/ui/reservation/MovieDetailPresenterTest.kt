package woowacourse.movie.ui.reservation

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Test
import woowacourse.movie.model.MovieState

class MovieDetailPresenterTest {
    @Test
    fun `표의 초기 값이 1이고 값을 추가하면 2가 된다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val movie: MovieState = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view, "cinemaName", movie)
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
        val movie: MovieState = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view, "cinemaName", movie)
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
    fun `영화 정보를 넘기면 상영 날짜 및 시간대를 반환한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val movie: MovieState = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view, "cinemaName", movie)

        every {
            movie.screeningTimes
        } returns listOf(LocalTime.parse("10:00"), LocalTime.parse("12:00"))

        every { view.setDateTimeSpinner(any(), movie.screeningTimes) } returns Unit

        // when
        presenter.getMovieRunningDateTimes()

        // then
        verify { view.setDateTimeSpinner(any(), movie.screeningTimes) }
    }

    @Test
    fun `예매하기 버튼을 누르면 좌석 선택 화면으로 이동한다`() {
        // given
        val view: MovieDetailActivity = mockk(relaxed = true)
        val movie: MovieState = mockk(relaxed = true)
        val presenter = MovieDetailPresenter(view, "cinemaName", movie)

        // when
        presenter.onReserveButtonClick()

        // then
        verify { view.navigateSeatSelectActivity(movie, "cinemaName") }
    }
}
