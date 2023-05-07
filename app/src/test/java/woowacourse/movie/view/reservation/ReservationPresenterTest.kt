package woowacourse.movie.view.reservation

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.domain.Minute
import woowacourse.movie.domain.Movie
import woowacourse.movie.view.mapper.toUiModel
import woowacourse.movie.view.model.MovieListModel
import java.time.LocalDate
import java.time.LocalTime

internal class ReservationPresenterTest {
    private lateinit var reservationPresenter: ReservationPresenter
    private lateinit var view: ReservationContract.View

    @Before
    fun setUp() {
        view = mockk()
        val movie = fakeMovie().toUiModel()
        reservationPresenter = ReservationPresenter(view).apply {
            this.movie = movie
        }
    }

    @Test
    fun `영화 정보를 띄운다`() {
        val slot = slot<MovieListModel.MovieUiModel>()
        val movie = fakeMovie().toUiModel()
        every { view.getMovie() } returns movie
        every { view.initMovieView(capture(slot)) } returns Unit

        reservationPresenter.loadMovie()

        val expected = fakeMovie().toUiModel()
        val actual = slot.captured

        assertEquals(expected, actual)
    }

    @Test
    fun `DateSpinner에 영화 상영기간에 해당하는 날짜를 세팅한다`() {
        val screeningDatesSlot = slot<List<LocalDate>>()
        val expected = listOf<LocalDate>(
            LocalDate.of(2024, 3, 1),
            LocalDate.of(2024, 3, 2),
            LocalDate.of(2024, 3, 3),
            LocalDate.of(2024, 3, 4),
            LocalDate.of(2024, 3, 5)
        )
        every { view.setUpDateSpinner(capture(screeningDatesSlot)) } returns Unit

        reservationPresenter.setUpScreeningDateTime()

        assertEquals(expected, screeningDatesSlot.captured)
    }

    @Test
    fun `선택한 날짜에 해당하는 시간대를 TimeSpinner에 세팅한다`() {
        val screeningTimeSlot = slot<List<LocalTime>>()
        every { view.setUpTimeSpinner(capture(screeningTimeSlot), 0) } returns Unit

        reservationPresenter.selectScreeningDate(
            LocalDate.of(2024, 3, 1),
            null
        )

        val expected = listOf(
            LocalTime.of(10, 0),
            LocalTime.of(12, 0),
            LocalTime.of(14, 0),
            LocalTime.of(16, 0),
            LocalTime.of(18, 0),
            LocalTime.of(20, 0),
            LocalTime.of(22, 0),
            LocalTime.of(0, 0)
        )
        assertEquals(expected, screeningTimeSlot.captured)
    }

    @Test
    fun `예매 인원을 하나 증가시키면 인원은 2명이다`() {
        val countSlot = slot<Int>()
        every { view.setPeopleCountTextView(capture(countSlot)) } returns Unit

        reservationPresenter.increasePeopleCount()

        assertEquals(2, countSlot.captured)
    }

    @Test
    fun `예매 인원 초기값 1에서 인원을 감소시키면 인원수 뷰가 변경되지 않는다`() {
        reservationPresenter.decreasePeopleCount()

        verify(exactly = 0) { view.setPeopleCountTextView(any()) }
    }

    private fun fakeMovie() = Movie(
        1,
        "해리 포터와 마법사의 돌",
        LocalDate.of(2024, 3, 1),
        LocalDate.of(2024, 3, 5),
        Minute(152),
        R.drawable.harry_potter1_poster,
        "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다."
    )
}
