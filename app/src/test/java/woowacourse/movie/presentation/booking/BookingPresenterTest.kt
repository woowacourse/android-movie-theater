package woowacourse.movie.presentation.booking

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.tools.Movie
import woowacourse.movie.domain.model.tools.TicketCount
import java.time.LocalDate
import java.time.LocalTime

class BookingPresenterTest {
    private lateinit var presenter: BookingPresenter
    private lateinit var view: BookingContract.View
    private lateinit var dummyMovie: Movie

    @Before
    fun initBookingPresenter() {
        view = mockk(relaxed = true)
        dummyMovie = Movie(
            1,
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 31),
            152,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
        )
        presenter = BookingPresenter(view = view, movie = dummyMovie)
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
        val specializedPresenter = BookingPresenter(view, TicketCount(2), dummyMovie)
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
        val specializedPresenter = BookingPresenter(view, TicketCount(3), dummyMovie)

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
        val dates = presenter.getScreeningDates()

        // then
        val actual = slot.captured
        assertEquals(dates, actual)
    }
}
