package woowacourse.movie.feature.home.theater

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.feature.firstMovie
import woowacourse.movie.feature.firstMovieId
import woowacourse.movie.feature.invalidMovieId

class TheaterSelectionPresenterTest {
    private lateinit var view: TheaterSelectionContract.View
    private lateinit var presenter: TheaterSelectionPresenter
    private val movie = firstMovie

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = TheaterSelectionPresenter(view)
    }

    @Test
    fun `영화 id에 맞는 극장들을 불러온다`() {
        // given
        every { view.setUpTheaterAdapter(any()) } just Runs

        // when
        presenter.loadTheaters(firstMovieId)

        // when
        verify { view.setUpTheaterAdapter(movie.theaters) }
    }

    @Test
    fun `존재하지 않는 영화 id의 경우 극장을 불러오면 에러 메시지를 보여준다`() {
        // given
        every { view.showToastInvalidMovieIdError(any()) } just Runs

        // when
        presenter.loadTheaters(invalidMovieId)

        // then
        verify { view.showToastInvalidMovieIdError(any()) }
    }
}
