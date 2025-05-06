package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.ContentService.getAllContents
import woowacourse.movie.data.ContentService.getMovieScreenings
import woowacourse.movie.data.ContentService.movies
import woowacourse.movie.feature.home.contract.HomeContract
import woowacourse.movie.feature.home.presenter.HomePresenter
import woowacourse.movie.feature.mapper.toUi

class MoviesPresenterTest {
    private lateinit var presenter: HomePresenter
    private lateinit var view: HomeContract.View

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = HomePresenter(view)
    }

    @Test
    fun `prepareContents 호출 시 영화 목록을 보여준다`() {
        // given & when
        presenter.prepareContents()

        // then
        verify {
            view.showContents(getAllContents().map { it.toUi() })
        }
    }

    @Test
    fun `selectMovieForBooking 호출 시 극장 목록을 보여준다`() {
        // given
        val movieUiModel = movies.first().toUi()
        val screening = getMovieScreenings(movieUiModel.title).toUi()

        // when
        presenter.selectMovieForBooking(movieUiModel)

        // then
        verify {
            view.showTheaters(screening)
        }
    }
}
