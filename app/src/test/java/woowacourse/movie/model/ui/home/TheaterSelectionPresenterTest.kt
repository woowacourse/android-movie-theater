package woowacourse.movie.model.ui.home

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.movieContent
import woowacourse.movie.ui.home.TheaterSelectionContract
import woowacourse.movie.ui.home.TheaterSelectionPresenter

class TheaterSelectionPresenterTest {
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionPresenter

    @BeforeEach
    fun setUp() {
        view = mockk<TheaterSelectionContract.View>(relaxed = true)
        presenter = TheaterSelectionPresenter(view, MovieContentsImpl)
    }

    @Test
    fun `극장 정보를 불러와 보여준다`() {
        // given

        // when
        presenter.loadTheaters(0L)

        // then
        verify {
            view.showTheaters(movieContent.theaters)
        }
    }

    @Test
    fun `극장을 선택하면 예약화면으로 이동한다`() {
        // given

        // when
        presenter.startReservation(0L, 0L)

        // then
        verify { view.navigateToMovieReservation(0L, 0L) }
    }
}
