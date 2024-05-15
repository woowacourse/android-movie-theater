package woowacourse.movie.ui.home

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.ui.repository.FakeMovieContentRepository
import woowacourse.movie.ui.repository.FakeTheaterRepository

class TheaterSelectionPresenterTest {
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionPresenter

    @BeforeEach
    fun setUp() {
        view = mockk<TheaterSelectionContract.View>(relaxed = true)
        presenter = TheaterSelectionPresenter(view, FakeMovieContentRepository, FakeTheaterRepository)
    }

    @Test
    fun `영화의 id를 넘겨주면 영화가 상영되는 극장 정보를 표출한다`() {
        // given

        // when
        presenter.loadTheaters(0L)

        // then
        verify { view.showTheaters(any(), any()) }
    }

    @Test
    fun `유효하지_않은_영화_아이디가_주어졌을_때_오류메시지를_표출한다`() {
        // given

        // when
        presenter.loadTheaters(-1L)

        // then
        verify {
            view.showError(any())
        }
    }
}
