package woowacourse.movie.presenter

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.Movie.Companion.movies
import woowacourse.movie.domain.model.Screenings.Companion.screenings
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
    fun `prepareMovies 호출 시 영화 목록을 보여준다`() {
        // given & when
        presenter.prepareMovies()

        // then
        verify {
            view.showMovies(movies.map { it.toUi() })
        }
    }

    @Test
    fun `selectMovieForBooking 호출 시 극장 목록을 보여준다`() {
        // given
        val movieUiModel = movies.first().toUi()
        val screening = screenings.getMovieScreenings(movieUiModel.title).toUi()

        // when
        presenter.selectMovieForBooking(movieUiModel)

        // then
        verify {
            view.showTheaters(screening)
        }
    }
}
