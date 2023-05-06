package woowacourse.movie.ui.main.cinemaListBottomSheet

import com.example.domain.model.Movie
import com.example.domain.repository.CinemaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.model.MovieState
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListContract
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListPresenter

class CinemaListPresenterTest {
    @Test
    fun `영화관 리스트를 가져올 수 있다`() {
        // given
        val view: CinemaListContract.View = mockk(relaxed = true)
        val cinemaRepository: CinemaRepository = mockk(relaxed = true)
        val movie: MovieState = mockk(relaxed = true)
        val presenter = CinemaListPresenter(view, cinemaRepository)
        val slot = slot<Movie>()
        every { cinemaRepository.allCinema() } returns listOf()
        every { cinemaRepository.findCinema(capture(slot)) } returns listOf()

        // when
        presenter.getCinemaList(movie)

        // then
        verify { cinemaRepository.findCinema(slot.captured) }
        verify { view.setAdapter(listOf()) }
    }
}
