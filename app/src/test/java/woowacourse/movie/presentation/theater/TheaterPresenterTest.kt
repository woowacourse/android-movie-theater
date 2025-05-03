package woowacourse.movie.presentation.theater

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieData
import woowacourse.movie.data.ScreeningData
import woowacourse.movie.domain.model.Screening
import java.time.LocalTime

class TheaterPresenterTest {
    private lateinit var view: TheaterContract.View
    private lateinit var presenter: TheaterPresenter
    private val movie = MovieData.movie1

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = TheaterPresenter(view, movie, ScreeningData)
    }

    @Test
    fun `극장 목록을 가져와서 화면에 출력한다`() {
        // When
        presenter.loadTheaterList()

        // Then
        verify { view.showTheaters(any()) }
    }

    @Test
    fun `극장을 선택하면 화면을 이동한다`() {
        // Given
        val screening =
            Screening(
                "선릉 극장",
                movie,
                listOf(LocalTime.of(12, 0)),
            )

        // When
        presenter.startBooking(screening)

        // Then
        verify { view.navigateToBooking(screening) }
    }
}
