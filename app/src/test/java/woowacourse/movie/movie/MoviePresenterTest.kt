package woowacourse.movie.movie

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.createMovie
import woowacourse.movie.mapper.toUiModel

class MoviePresenterTest {
    private lateinit var presenter: MoviePresenter
    private lateinit var mockView: MovieContract.View

    @BeforeEach
    fun setUp() {
        mockView = mockk(relaxed = true)
        presenter = MoviePresenter(view = mockView)
    }

    @Test
    fun `영화를 고르면 영화관 선택 다이얼로그를 띄운다`() {
        // given
        val movieUiData = createMovie(HARRY_POTTER).toUiModel()

        // when
        presenter.selectMovie(movieUiData)

        // then
        verify { mockView.showTheaterDialog(any(), movieUiData) }
    }
}
