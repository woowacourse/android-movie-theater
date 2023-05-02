package woowacourse.movie.ui.activity.detail.presenter

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.ui.activity.detail.MovieDetailContract

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
}
