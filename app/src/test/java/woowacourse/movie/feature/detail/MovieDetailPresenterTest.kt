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
    fun `영화 id에 해당하는 영화를 불러온다`() {
        // given
        every { view.displayMovieDetail(any()) } just runs
        every { view.updateReservationCountView(any()) } just runs

        // when
        presenter.loadMovieDetail(firstMovieId)

        // then
        verify { view.displayMovieDetail(movie) }
        every { view.updateReservationCountView(any()) } just runs
    }

    @Test
    fun `존재하지 않는 영화 id의 경우 영화를 불러오면 에러 메시지를 보여준다`() {
        // given
        every { view.showToastInvalidMovieIdError(any()) } just runs

        // when
        presenter.loadMovieDetail(invalidMovieId)

        // then
        every { view.showToastInvalidMovieIdError(any()) } just runs
    }

    @Test
    fun `예매 개수가 1인 경우 예매 개수를 증가시키면 2가 된다`() {
        // given
        every { view.updateReservationCountView(any()) } just runs

        // when
        presenter.increaseReservationCount()

        // then
        verify { view.updateReservationCountView(2) }
    }

    @Test
    fun `예매 개수가 1인 경우 예매 개수를 감소시키면 감소되지 않는다`() {
        // given
        every { view.updateReservationCountView(any()) } just runs

        // when
        presenter.decreaseReservationCount()

        // then
        verify { view.updateReservationCountView(1) }
    }

    @Test
    fun `예매 개수가 2인 경우 예매 개수를 감소시키면 1이 된다`() {
        // given
        every { view.updateReservationCountView(any()) } just runs
        presenter.increaseReservationCount()

        // when
        presenter.decreaseReservationCount()

        // then
        verify { view.updateReservationCountView(2) }
    }

    @Test
    fun `영화를 예매한다`() {
        // given
        every { view.navigateToSeatSelectionView(any(), any(), any(), any()) } just runs

        // when
        presenter.reserveMovie(0, "2024-04-01", "10:00")

        // then
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
