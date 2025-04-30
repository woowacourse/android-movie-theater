package woowacourse.movie.presentation.theater

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.MovieData
import woowacourse.movie.domain.model.ScreeningInfo
import java.time.LocalTime

class TheaterPresenterTest {
    private lateinit var view: TheaterContract.View
    private lateinit var presenter: TheaterPresenter
    private val movie = MovieData.movie1

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = TheaterPresenter(view, movie)
    }

    @Test
    fun `극장 목록을 가져와서 화면에 출력한다`() {
        // When
        presenter.onViewCreated()

        // Then
        verify { view.showTheaters(any()) }
    }

    @Test
    fun `극장을 선택하면 화면을 이동한다`() {
        // Given
        val screeningInfo = ScreeningInfo(
            "선릉 극장",
            movie,
            listOf(LocalTime.of(12, 0))
        )

        // When
        presenter.onTheaterClicked(screeningInfo)

        // Then
        verify { view.navigateToBooking(screeningInfo) }
    }
}