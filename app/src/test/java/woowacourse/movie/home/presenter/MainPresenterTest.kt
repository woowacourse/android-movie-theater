package woowacourse.movie.home.presenter

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.presentation.main.home.HomeContract
import woowacourse.movie.presentation.main.home.HomePresenter

class MainPresenterTest {
    private lateinit var view: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<HomeContract.View>()
        presenter = HomePresenter(view)
    }

    @Test
    fun `영화 정보를 목록에 띄워야 한다`() {
        every { view.showMoviesInfo(any(), any()) } just runs
        // when
        presenter.setMoviesInfo()
        // then
        verify { view.showMoviesInfo(any(), any()) }
    }

    @Test
    fun `목록이 클릭되면 다음 화면으로 넘어가야 한다`() {
        every { view.setOnListViewClickListener() } just runs
        // when
        presenter.setListViewClickListenerInfo()
        // then
        verify { view.setOnListViewClickListener() }
    }
}
