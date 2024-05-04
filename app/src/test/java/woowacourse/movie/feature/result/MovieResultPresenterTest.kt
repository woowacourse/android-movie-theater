package woowacourse.movie.feature.result

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MovieResultPresenterTest {
    private lateinit var view: MovieResultContract.View
    private lateinit var presenter: MovieResultPresenter

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MovieResultPresenter(view)
    }

    @Test
    fun `loadMovieTicket를 호출하면 예매한 영화 티켓의 정보가 보여진다`() {
        // Given
        every { view.displayMovieTicket(any()) } just runs

        // When
        presenter.loadMovieTicket(1L, "2024-04-01", "10:00", 3, "A0, A1, A2", "선릉")

        // Then
        verify { view.displayMovieTicket(any()) }
    }

    @Test
    fun `영화 데이터가 없는 경우 loadMovieTicket를 호출하면 에러 메시지를 보여준다`() {
        // Given
        every { view.handleInvalidMovieIdError(any()) } just runs

        // When
        presenter.loadMovieTicket(-1L, "2024-04-01", "10:00", 3, "A0, A1, A2", "선릉")

        // Then
        verify { view.handleInvalidMovieIdError(any()) }
    }
}
