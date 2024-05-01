package woowacourse.movie.model.ui.home

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.model.data.MovieContentsImpl
import woowacourse.movie.model.data.TheatersImpl
import woowacourse.movie.model.movieContent
import woowacourse.movie.ui.home.TheaterSelectionContract
import woowacourse.movie.ui.home.TheaterSelectionPresenter

class TheaterSelectionPresenterTest {
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionPresenter

    @BeforeEach
    fun setUp() {
        view = mockk<TheaterSelectionContract.View>(relaxed = true)
        presenter = TheaterSelectionPresenter(view, MovieContentsImpl, TheatersImpl)
    }

    @Test
    fun `유효하지_않은_영화_아이디가_주어졌을_때_극장 정보_다이얼로그를_닫는다`() {
        // given

        // when
        presenter.loadTheaters(-1L)

        // then
        verify {
            view.dismissDialog()
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
