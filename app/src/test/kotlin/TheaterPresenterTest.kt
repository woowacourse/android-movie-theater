import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import woowacourse.movie.data.DummyMovies
import woowacourse.movie.model.MovieTheater
import woowacourse.movie.screeningmovie.theaters.TheaterContract
import woowacourse.movie.screeningmovie.theaters.TheaterPresenter
import woowacourse.movie.screeningmovie.theaters.toTheaterUiModel

class TheaterPresenterTest {
    private lateinit var view: TheaterContract.View

    private lateinit var presenter: TheaterPresenter

    @BeforeEach
    fun setUp() {
        view = mockk<TheaterContract.View>()
        presenter = TheaterPresenter(DummyMovies, view)
    }

    @Test
    @DisplayName("moveId를 이용해 상영 정보를 불러오면, 극장에 관한 데이터로 정제해서 뷰에게 전달한다.")
    fun deliver_formatted_theater_data_When_load_screen_data() {
        // when
        val expectedTheater =
            listOf(
                MovieTheater.STUB_A,
                MovieTheater.STUB_B,
                MovieTheater.STUB_C,
            )

        val expectedUiModels =
            expectedTheater.map { theater ->
                val screeningMovie = DummyMovies.screenMovieById(MOVIE_ID, theater.id)
                screeningMovie.theater.toTheaterUiModel(screeningMovie.totalScreeningTimesNum())
            }
        every { view.showTheaters(expectedUiModels) } just Runs

        // given
        presenter.loadTheaters(MOVIE_ID)

        // then
        verify(exactly = 1) { view.showTheaters(expectedUiModels) }
    }

    @Test
    @DisplayName("영화 정보와 극장 정보를 받으면, 그에 대응되는 상영 정보를 찾아서 뷰에게 전달한다.")
    fun deliver_screen_id_When_receive_movie_and_theater_Id() {
        // when
        val expectedScreenId = 0L
        every { view.navigateMovieReservation(expectedScreenId, THEATER_ID) } just Runs

        // given
        presenter.selectTheater(MOVIE_ID, THEATER_ID)

        // then
        verify { view.navigateMovieReservation(expectedScreenId, THEATER_ID) }
    }

    companion object {
        private const val MOVIE_ID = 1L
        private const val THEATER_ID = 0L
    }
}
