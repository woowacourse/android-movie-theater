package woowacourse.movie.movie

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.fixture.HARRY_POTTER
import woowacourse.movie.fixture.createMovie
import woowacourse.movie.mapper.toUiModel
import woowacourse.movie.ui.model.MovieUiModel
import woowacourse.movie.ui.model.TheaterUiModel

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
        val theaterSlot = slot<ArrayList<TheaterUiModel>>()
        val movieSlot = slot<MovieUiModel>()

        every {
            mockView.showTheaterDialog(capture(theaterSlot), capture(movieSlot))
        } just Runs

        // when
        presenter.selectMovie(movieUiData)

        // then
        verify(exactly = 1) { mockView.showTheaterDialog(any(), any()) }

        assertThat(movieSlot.captured).isEqualTo(movieUiData)
        assertThat(theaterSlot.captured).isNotEmpty
        assertThat(theaterSlot.captured.all { it.screeningInfo.movie == movieUiData }).isTrue
    }
}
