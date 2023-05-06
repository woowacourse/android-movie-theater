package woowacourse.movie.presentation.views.main.fragments.home

import io.mockk.mockk
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.views.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.views.main.fragments.home.contract.presenter.HomePresenter

class HomeFragmentTest {
    private lateinit var presenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = HomePresenter()
        presenter.attach(view)
    }

//    @Test
//    internal fun 지정한만큼_영화를_더_불러오고_화면에_추가한다() {
//        // given
//        val slot = slot<List<ListItem>>()
//        val movieLoadSize = 10
//        every { view.showMoreMovies(capture(slot)) } answers { nothing }
//
//        // when
//        presenter.loadMoreMovies(movieLoadSize)
//
//        // then
//        assertEquals(movieLoadSize, slot.captured.size)
//    }

    @Test
    internal fun 영화_아이템을_클릭하면_영화_티켓_화면을_보여준다() {
        // given
        val item = mockk<Movie>()

        // when
        presenter.onItemClick(item)

        // then
        verify(exactly = 1) { view.showTheaterPickerScreen(item) }
    }

    @Test
    internal fun 광고_아이템을_클릭하면_광고_화면을_보여준다() {
        // given
        val item = mockk<Ad>()

        // when
        presenter.onItemClick(item)

        // then
        verify(exactly = 1) { view.showAdWebSite(item) }

    }
}
