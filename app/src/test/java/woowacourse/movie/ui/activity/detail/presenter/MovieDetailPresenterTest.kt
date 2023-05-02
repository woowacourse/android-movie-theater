package woowacourse.movie.ui.activity.detail.presenter

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ui.activity.detail.MovieDetailContract
import java.time.LocalDate
import java.time.LocalTime

internal class MovieDetailPresenterTest {
    private lateinit var presenter: MovieDetailPresenter
    private lateinit var view: MovieDetailContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(view)
    }

    @Test
    fun `5명으로 인원수를 설정하면 인원수는 5명으로 설정된다`() {
        // given
        justRun { view.setPeopleCount(any()) }

        // when
        presenter.setPeopleCount(5)

        // then
        verify { view.setPeopleCount(5) }
    }

    @Test
    fun `1명에서 인원수가 감소해도 인원수는 1명이다`() {
        // given
        justRun { view.setPeopleCount(any()) }
        presenter.setPeopleCount(1)

        // when
        presenter.decreasePeopleCount()

        // then
        verify { view.setPeopleCount(1) }
    }

    @Test
    fun `2명에서 인원수가 감소하면 인원수는 1명이다`() {
        // given
        justRun { view.setPeopleCount(any()) }
        presenter.setPeopleCount(2)

        // when
        presenter.decreasePeopleCount()

        // then
        verify { view.setPeopleCount(1) }
    }

    @Test
    fun `1명에서 인원수가 증가하면 인원수는 2명이다`() {
        // given
        justRun { view.setPeopleCount(any()) }
        presenter.setPeopleCount(1)

        // when
        presenter.increasePeopleCount()

        // then
        verify { view.setPeopleCount(2) }
    }

    @Test
    fun `평일로 날짜를 설정하면 시간은 2시간 간격으로 9시부터 23시까지 이다`() {
        // given
        justRun { view.initTimeSpinner(any()) }

        // when
        presenter.initTimes(LocalDate.of(2023, 5, 2))
        val expected = listOf<LocalTime>(
            LocalTime.of(9, 0), LocalTime.of(11, 0),
            LocalTime.of(13, 0), LocalTime.of(15, 0),
            LocalTime.of(17, 0), LocalTime.of(19, 0),
            LocalTime.of(21, 0), LocalTime.of(23, 0),
        )

        // then
        verify { view.initTimeSpinner(expected) }
    }

    @Test
    fun `주말로 날짜를 설정하면 시간은 2시간 간격으로 10시부터 22시까지 이다`() {
        // given
        justRun { view.initTimeSpinner(any()) }

        // when
        presenter.initTimes(LocalDate.of(2023, 5, 6))
        val expected = listOf<LocalTime>(
            LocalTime.of(10, 0), LocalTime.of(12, 0),
            LocalTime.of(14, 0), LocalTime.of(16, 0),
            LocalTime.of(18, 0), LocalTime.of(20, 0),
            LocalTime.of(22, 0),
        )

        // then
        verify { view.initTimeSpinner(expected) }
    }

    @Test
    fun `날짜를 바꾸면 타임 스피너가 업데이트 된다`() {
        // given
        justRun { view.initTimeSpinner(any()) }
        justRun { view.updateTimeSpinner() }
        presenter.initTimes(LocalDate.of(2023, 5, 2))

        // when
        presenter.updateTimes(LocalDate.of(2023, 5, 6))

        // then
        verify { view.updateTimeSpinner() }
    }
}
