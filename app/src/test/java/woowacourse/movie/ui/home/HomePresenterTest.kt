package woowacourse.movie.ui.home

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.repository.FakeMovieRepository
import woowacourse.movie.domain.repository.FakeScreenRepository

class HomePresenterTest {
    private lateinit var mockView: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @BeforeEach
    fun setUp() {
        mockView = mockk<HomeContract.View>()
        presenter =
            HomePresenter(
                view = mockView,
                movieRepository = FakeMovieRepository(),
                screenRepository = FakeScreenRepository(),
            )
    }

    @Test
    fun `영화들을 표시`() {
        every { mockView.showScreens(any()) } just runs

        presenter.loadScreen()

        verify(exactly = 1) { mockView.showScreens(any()) }
    }

    @Test
    fun `영화관들을 표시`() {
        // given
        every { mockView.showTheaters(any()) } just runs

        // when
        presenter.loadTheaters(1)

        // then
        verify(exactly = 1) { mockView.showTheaters(any()) }
    }
}