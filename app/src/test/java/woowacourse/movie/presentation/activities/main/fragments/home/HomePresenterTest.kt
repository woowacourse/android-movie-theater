package woowacourse.movie.presentation.activities.main.fragments.home

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.item.Ad
import woowacourse.movie.presentation.model.item.Movie

internal class HomePresenterTest {

    private lateinit var view: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @Before
    fun setup() {
        view = mockk()
        presenter = HomePresenter(view)
    }

    @Test
    fun `영화데이터를 불러와 화면에 보여준다`() {
        // given
        justRun { view.showMovieList(any()) }

        // when
        presenter.loadData()

        // then
        verify { view.showMovieList(any()) }
    }

    @Test
    fun `영화를 클릭하면 극장 선택 화면으로 넘어간다`() {
        // given
        val movie: Movie = mockk()
        justRun { view.moveTheaterList(movie) }

        // when
        presenter.moveNext(movie)

        // then
        verify { view.moveTheaterList(movie) }
        verify(exactly = 0) { view.moveAdWebPage(any()) }
    }

    @Test
    fun `광고를 클릭하면 광고 화면으로 넘어간다`() {
        // given
        val ad: Ad = mockk()
        justRun { view.moveAdWebPage(ad) }

        // when
        presenter.moveNext(ad)

        // then
        verify { view.moveAdWebPage(ad) }
        verify(exactly = 0) { view.moveTheaterList(any()) }
    }
}
