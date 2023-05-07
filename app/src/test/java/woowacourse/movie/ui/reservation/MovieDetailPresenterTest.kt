package woowacourse.movie.ui.reservation

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalTime
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MovieState

class MovieDetailPresenterTest {
    private lateinit var view: MovieDetailActivity
    private lateinit var cinemaName: String
    private lateinit var movie: MovieState

    private lateinit var presenter: MovieDetailPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        cinemaName = "CGV"
        movie = mockk(relaxed = true)
        presenter = MovieDetailPresenter(view, cinemaName, movie)
    }

    @Test
    fun `표의 초기 값이 1이고 값을 추가하면 2가 된다`() {
        // given
        val slot = slot<Int>()
        every { view.setCountText(capture(slot)) } answers { println("slot : ${slot.captured}") }
        assertEquals(presenter.count.value, 1)

        // when
        presenter.plusCount()

        // then
        assertEquals(slot.captured, 2)
        verify { view.setCountText(slot.captured) }
    }

    @Test
    fun `표가 2장이상일 때 1씩 떨어진다`() {
        // given
        val slot = slot<Int>()
        every { view.setCountText(capture(slot)) } answers { println("slot : ${slot.captured}") }
        presenter.plusCount()
        assertEquals(presenter.count.value, 2)

        // when
        presenter.minusCount()

        // then
        assertEquals(slot.captured, 1)
        verify { view.setCountText(slot.captured) }
    }

    @Test
    fun `표는 1장 밑으로 떨어지지 않는다`() {
        // given
        val slot = slot<Int>()
        every { view.setCountText(capture(slot)) } answers { println("slot : ${slot.captured}") }
        assertEquals(presenter.count.value, 1)

        // when
        presenter.minusCount()

        // then
        assertEquals(slot.captured, 1)
        verify { view.setCountText(slot.captured) }
    }

    @Test
    fun `영화 정보를 넘기면 상영 날짜 및 시간대를 반환한다`() {
        // given
        every {
            movie.screeningTimes
        } returns listOf(LocalTime.parse("10:00"), LocalTime.parse("12:00"))

        every { view.initDateTimeSpinner(any(), movie.screeningTimes) } returns Unit

        // when
        presenter.setUpDateTime()

        // then
        verify { view.initDateTimeSpinner(any(), movie.screeningTimes) }
    }

    @Test
    fun `예매하기 버튼을 누르면 좌석 선택 화면으로 이동한다`() {
        // when
        presenter.submitReservation()

        // then
        verify { view.navigateToSeatSelectActivity(movie, cinemaName) }
    }
}
