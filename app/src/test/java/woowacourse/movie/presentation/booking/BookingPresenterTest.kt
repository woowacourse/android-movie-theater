package woowacourse.movie.presentation.booking

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.tools.TicketCount
import woowacourse.movie.model.data.remote.DummyMovieStorage
import java.time.LocalDate
import java.time.LocalTime

class BookingPresenterTest {
    private lateinit var presenter: BookingPresenter
    private lateinit var view: BookingContract.View

    @Before
    fun initBookingPresenter() {
        view = mockk(relaxed = true)
        presenter = BookingPresenter(view = view, movieStorage = DummyMovieStorage())
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
        val specializedPresenter = BookingPresenter(view, TicketCount(2), DummyMovieStorage())
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
        val specializedPresenter = BookingPresenter(view, TicketCount(3), DummyMovieStorage())

        // when
        val actual = specializedPresenter.getTicketCurrentCount()

        // then
        assertEquals(3, actual)
    }

    @Test
    fun `날짜에 맞는 시간을 계산하면 시간 스피너 뷰 리스트가 업데이트 된다`() {
        // given
        val slot = slot<List<LocalTime>>()
        every { view.setTimeSpinnerItems(capture(slot)) } just Runs
        val testDate = LocalDate.of(2023, 5, 4)

        // when
        val times = presenter.getScreeningTimes(testDate)

        // then
        val actual = slot.captured
        assertEquals(times, actual)
    }

    @Test
    fun `상영기간을 받아서 상영일 리스트로 변환하면 날짜 스피너 뷰 리스트가 업데이트 된다`() {
        // given
        val slot = slot<List<LocalDate>>()
        every { view.setDateSpinnerItems(capture(slot)) } just Runs

        // when
        // movieId 임의대로 넣은것 추후 DummyMovieStroage 서버로 변경시 같이 Dummy값에 맞춰서 대응해야함
        val dates = presenter.getScreeningDates(1)

        // then
        val actual = slot.captured
        assertEquals(dates, actual)
    }
}
