package woowacourse.movie.domain

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.view.home.HomeContract
import woowacourse.movie.view.home.HomePresenter

class HomePresenterTest {
    private lateinit var view: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = HomePresenter(view)
    }

    @Test
    fun `데이터를 가져오면 화면에 띄워진다`() {
        // given
        every { view.showMovies(any()) } just Runs

        presenter.loadMovies()

        // when & then
        verify { view.showMovies(any()) }
    }
}
