package woowacourse.movie.presentation.activities.main.fragments.home

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.activities.main.fragments.home.contract.HomeContract
import woowacourse.movie.presentation.activities.main.fragments.home.contract.presenter.HomePresenter
import woowacourse.movie.presentation.model.movieitem.Ad
import woowacourse.movie.presentation.model.movieitem.ListItem
import woowacourse.movie.presentation.model.movieitem.Movie

class HomeFragmentTest {
    private lateinit var presenter: HomeContract.Presenter
    private lateinit var view: HomeContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = HomePresenter()
        presenter.attach(view)
    }

    @Test
    internal fun 지정한만큼_영화를_더_불러오고_화면에_추가한다() {
        // given
        val slot = slot<List<ListItem>>()
        val movieLoadSize = 10
        every { view.showMoreMovies(capture(slot)) } answers { nothing }

        // when
        presenter.loadMoreMovies(movieLoadSize)

        // then
        assertEquals(movieLoadSize, slot.captured.size)
    }

    @Test
    internal fun 영화_아이템을_클릭하면_영화_티켓_화면을_보여준다() {
        // given
        val item = mockk<Movie>()

        // when
        presenter.onItemClick(item)

        // then
        verify(exactly = 1) { view.showTicketScreen(item) }
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
