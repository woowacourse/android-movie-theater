package woowacourse.movie.feature.detail.dateTime

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.R
import woowacourse.movie.model.MovieState
import woowacourse.movie.model.TheaterMovieState
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

internal class DateTimePresenterTest {
    private lateinit var view: DateTimeContract.View
    private lateinit var presenter: DateTimeContract.Presenter

    @Before
    fun init() {
        view = mockk()
        every { view.setDateSpinnerAdapter(any()) } just Runs
        every { view.setTimeSpinnerAdapter(any()) } just Runs
        presenter = DateTimePresenter(view, mockTheaterMovie)
    }

    @Test
    fun 스피너에서_날짜를_변경하면_선택_날짜가_바뀐다() {
        presenter.clickDate(1)

        val expected = LocalDate.of(2023, 5, 4)
        assert(presenter.selectDate == expected)
    }

    @Test
    fun 스피너에서_시간을_변경하면_선택_시간이_바뀐다() {
        presenter.clickTime(1)

        val expected = LocalTime.of(16, 0)
        assert(presenter.selectTime == expected)
    }

    @Test
    fun 날짜시간을_조정시키면_선택날짜시간이_변경된다() {
        every { view.setSelectDate(any()) } just Runs
        every { view.setSelectTime(any()) } just Runs
        presenter.setDateTime(LocalDateTime.of(2023, 5, 6, 16, 0))

        assert(presenter.selectDate == LocalDate.of(2023, 5, 6))
        assert(presenter.selectTime == LocalTime.of(16, 0))
        verify { view.setSelectDate(any()) }
        verify { view.setSelectTime(any()) }
    }

    private val mockTheaterMovie = TheaterMovieState(
        "선릉 극장",
        MovieState(
            R.drawable.ga_oh_galaxy_poster,
            1,
            "가디언즈 오브 갤럭시: Volume 3",
            LocalDate.of(2023, 5, 3),
            LocalDate.of(2023, 7, 20),
            150,
            "‘가모라’를 잃고 슬픔에 빠져 있던 ‘피터 퀼’이 위기에 처한 은하계와 동료를 지키기 위해 다시 한번 가디언즈 팀과 힘을 모으고, 성공하지 못할 경우 그들의 마지막이 될지도 모르는 미션에 나서는 이야기"
        ),
        listOf(
            LocalTime.of(12, 0),
            LocalTime.of(16, 0),
            LocalTime.of(20, 0),
        )
    )
}
