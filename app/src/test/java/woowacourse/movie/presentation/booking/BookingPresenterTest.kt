package woowacourse.movie.presentation.booking

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.model.data.remote.DummyMovieStorage
import woowacourse.movie.model.data.storage.MovieTheaterStorage
import java.time.LocalTime

class BookingPresenterTest {
    private lateinit var presenter: BookingPresenter
    private lateinit var view: BookingContract.View
    private lateinit var movieTheaterStorage: MovieTheaterStorage

    @Before
    fun initBookingPresenter() {
        view = mockk(relaxed = true)
        movieTheaterStorage = mockk(relaxed = true)
        presenter = BookingPresenter(
            view = view,
            movieStorage = DummyMovieStorage(),
            movieTheaterStorage = movieTheaterStorage
        )
    }

    @Test
    fun `TicketCount 를 더해주면 관련 뷰의 값이 최신화된다`() {
        // given
        val slot = slot<Int>()
        every { view.setTicketCount(capture(slot)) } just Runs

        // when
        presenter.plusTicketCount()

        // then
        val actual = slot.captured
        assertEquals(2, actual)
    }

    @Test
    fun `TicketCount 를 빼주면 관련 뷰의 값이 최신화된다`() {
        // given
        val specializedPresenter =
            BookingPresenter(view, TicketCount(2), DummyMovieStorage(), movieTheaterStorage)
        val slot = slot<Int>()
        every { view.setTicketCount(capture(slot)) } just Runs

        // when
        specializedPresenter.minusTicketCount()

        // then
        val actual = slot.captured
        assertEquals(1, actual)
    }

    @Test
    fun `현재 TicketCount의 값이 3이라면 갯수를 조회했을때 3이 반환된다`() {
        // given
        val specializedPresenter =
            BookingPresenter(view, TicketCount(3), DummyMovieStorage(), movieTheaterStorage)

        // when
        val actual = specializedPresenter.getTicketCurrentCount()

        // then
        assertEquals(3, actual)
    }

    @Test
    fun `영화관에 맞는 시간표를 받아와 시간 스피너 뷰 리스트가 업데이트 된다`() {
        // given
        every { view.setTimeSpinnerItems(any()) } just Runs
        every { movieTheaterStorage.getTheaterTimeTableByMovieId(any(), any()) } returns listOf(
            LocalTime.of(9, 0)
        )

        // when
        val times = presenter.getScreeningTimes(1, "강남")

        // then
        verify(exactly = 1) { view.setTimeSpinnerItems(any()) }
    }

    @Test
    fun `상영기간을 받아서 상영일 리스트로 변환하면 날짜 스피너 뷰 리스트가 업데이트 된다`() {
        // given
        every { view.setDateSpinnerItems(any()) } just Runs

        // when
        // movieId 임의대로 넣은것 추후 DummyMovieStroage 서버로 변경시 같이 Dummy값에 맞춰서 대응해야함
        presenter.getScreeningDates(1)

        // then
        verify(exactly = 1) { view.setDateSpinnerItems(any()) }
    }
}
