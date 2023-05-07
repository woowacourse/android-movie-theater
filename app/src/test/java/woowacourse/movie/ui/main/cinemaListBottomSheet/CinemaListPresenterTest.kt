package woowacourse.movie.ui.main.cinemaListBottomSheet

import com.example.domain.model.Movie
import com.example.domain.repository.CinemaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListContract
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListPresenter

class CinemaListPresenterTest {
    private lateinit var view: CinemaListContract.View
    private lateinit var cinemaRepository: CinemaRepository
    private lateinit var movie: MovieState
    private lateinit var presenter: CinemaListPresenter

    @Before
    fun setUp() {
        // given
        view = mockk(relaxed = true)
        cinemaRepository = mockk(relaxed = true)
        movie = mockk(relaxed = true)
        presenter = CinemaListPresenter(view, cinemaRepository, movie)
    }

    @Test
    fun `영화관 리스트를 가져올 수 있다`() {
        // given
        val slot = slot<Movie>()
        every { cinemaRepository.findCinema(capture(slot)) } returns listOf()

        // when
        presenter.setUpCinemaList()

        // then
        verify { cinemaRepository.findCinema(slot.captured) }
        verify { view.setCinemaList(listOf(), movie) }
    }
}
