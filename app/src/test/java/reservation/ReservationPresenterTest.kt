package reservation

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.movie.ScreeningDateTimes
import woowacourse.movie.view.model.MovieUiModel
import woowacourse.movie.view.reservation.ReservationContract
import woowacourse.movie.view.reservation.ReservationPresenter
import java.time.LocalDate
import java.time.LocalTime

class ReservationPresenterTest {
    private lateinit var presenter: ReservationContract.Presenter
    private lateinit var view: ReservationContract.View

    @Before
    fun setUp() {
        view = mockk()
        val movieUiModel = MovieUiModel(
            "해리 포터와 마법사의 돌",
            LocalDate.of(2024, 4, 1),
            LocalDate.of(2024, 4, 2),
            152,
            0,
            "《해리 포터와 마법사의 돌》은 2001년 J. K. 롤링의 동명 소설을 원작으로 하여 만든, 영국과 미국 합작, 판타지 영화이다. 해리포터 시리즈 영화 8부작 중 첫 번째에 해당하는 작품이다. 크리스 콜럼버스가 감독을 맡았다.",
            mapOf("선릉 극장" to listOf(LocalTime.of(13, 0))),
        )
        presenter = ReservationPresenter(view, movieUiModel, "선릉 극장")
    }

    @Test
    fun 모든_상영_일정을_띄울_수_있다() {
        val slot = slot<List<LocalDate>>()
        every { view.showScreeningDate(capture(slot)) } just runs

        presenter.fetchScreeningDates()

        val expected = ScreeningDateTimes.of(
            LocalDate.of(2024, 4, 1),
            LocalDate.of(2024, 4, 2),
            listOf(LocalTime.of(13, 0)),
        ).dateTimes.keys.toList()

        assertEquals(expected, slot.captured)
        verify { view.showScreeningDate(expected) }
    }

    @Test
    fun 빼기_버튼을_클릭하면_1_감소한다() {
        val slot = slot<Int>()
        every { view.setCount(capture(slot)) } just runs
        presenter.plusCount()
        presenter.minusCount()
        assertEquals(1, slot.captured)
        verify { view.setCount(slot.captured) }
    }

    @Test
    fun 더하기_버튼을_클릭하면_1_증가한다() {
        val slot = slot<Int>()
        every { view.setCount(capture(slot)) } just runs
        presenter.plusCount()
        assertEquals(2, slot.captured)
        verify { view.setCount(slot.captured) }
    }

    @Test
    fun 날짜_스피너_값이_바뀌면_해당_날짜의_시간대로_시간_스피너_값이_바뀐다() {
        val dateSelected = LocalDate.of(2024, 4, 1)
        val slot = slot<List<LocalTime>>()
        every { view.showScreeningTimes(capture(slot)) } just runs

        presenter.fetchScreeningTimes(dateSelected)

        val expected = ScreeningDateTimes.of(
            LocalDate.of(2024, 4, 1),
            LocalDate.of(2024, 4, 2),
            listOf(LocalTime.of(13, 0)),
        ).dateTimes[dateSelected]

        assertEquals(expected, slot.captured)
        verify { view.showScreeningTimes(slot.captured) }
    }
}
