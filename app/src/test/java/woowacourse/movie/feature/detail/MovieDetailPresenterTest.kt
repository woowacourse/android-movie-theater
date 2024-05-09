package woowacourse.movie.feature.detail

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.feature.firstMovie
import woowacourse.movie.feature.firstMovieId
import woowacourse.movie.feature.invalidMovieId
import java.time.LocalDate
import java.time.LocalTime

class MovieDetailPresenterTest {
    private lateinit var view: MovieDetailContract.View
    private lateinit var presenter: MovieDetailPresenter
    private val movie = firstMovie

    @BeforeEach
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(view)
    }

    @Test
    fun `loadMovieDetail를 호출하면 view에서 영화 리스트를 보여준다`() {
        // Given
        every { view.displayMovieDetail(any()) } just runs
        every { view.updateReservationCountView(any()) } just runs

        // When
        presenter.loadMovieDetail(firstMovieId)

        // Then
        verify { view.displayMovieDetail(movie) }
        every { view.updateReservationCountView(any()) } just runs
    }

    @Test
    fun `영화 데이터가 없는 경우 loadMovieDetail를 호출하면 에러 메시지를 보여준다`() {
        // Given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // When
        presenter.loadMovieDetail(invalidMovieId)

        // Then
        every { view.showToastInvalidMovieIdError(any()) } just runs
    }

    @Test
    fun `plusReservationCount를 호출하면 카운트가 1만큼 증가한다`() {
        // Given
        every { view.updateReservationCountView(any()) } just runs

        // When
        presenter.plusReservationCount()

        // Then
        verify { view.updateReservationCountView(2) }
    }

    @Test
    fun `count가 1일때 minusReservationCount를 호출하면 카운트가 감소되지 않는다`() {
        // Given
        every { view.updateReservationCountView(any()) } just runs

        // When
        presenter.minusReservationCount()

        // Then
        verify { view.updateReservationCountView(1) }
    }

    @Test
    fun `minusReservationCount를 호출하면 카운트가 감소된다`() {
        // Given
        every { view.updateReservationCountView(any()) } just runs

        // When
        presenter.plusReservationCount()
        presenter.plusReservationCount()
        presenter.minusReservationCount()

        // Then
        verify { view.updateReservationCountView(2) }
    }

    @Test
    fun `reserveMovie를 호출하면 SeatSelectionView로 이동한다`() {
        // Given
        every { view.navigateToSeatSelectionView(any(), any(), any(), any()) } just runs

        // When
        presenter.reserveMovie(0, "2024-04-01", "10:00")

        // Then
        verify {
            view.navigateToSeatSelectionView(
                0,
                LocalDate.of(2024, 4, 1),
                LocalTime.of(10, 0),
                any(),
            )
        }
    }
}
