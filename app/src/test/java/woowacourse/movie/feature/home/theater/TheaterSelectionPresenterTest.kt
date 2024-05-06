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
    fun `loadTheaters를 호출하면 view에서 TheaterAdapter를 세팅한다`() {
        // Given
        every { view.setUpTheaterAdapter(any()) } just Runs

        // When
        presenter.loadTheaters(firstMovieId)

        // Then
        verify { view.setUpTheaterAdapter(movie.theaters) }
    }

    @Test
    fun `영화 데이터가 없는 경우 loadTheaters를 호출하면 에러 메시지를 보여준다`() {
        // Given
        every { view.showToastInvalidMovieIdError(any()) } just Runs

        // When
        presenter.loadTheaters(invalidMovieId)

        // Then
        verify { view.showToastInvalidMovieIdError(any()) }
    }
}
