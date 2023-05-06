package woowacourse.movie.ui.main.cinemaListBottomSheet

import android.net.Uri
import com.example.domain.model.Movie
import com.example.domain.repository.CinemaRepository
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import java.time.LocalDate
import java.time.LocalTime
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
        val presenter = CinemaListPresenter(view, cinemaRepository)
        val slot = slot<Movie>()
        every { cinemaRepository.allCinema() } returns listOf()
        every { cinemaRepository.findCinema(capture(slot)) } returns listOf()

        // when
        presenter.getCinemaList(sampleMovieState)

        // then
        verify { cinemaRepository.findCinema(slot.captured) }
        verify { view.setAdapter(listOf()) }
    }

    companion object {
        val sampleMovieState = MovieState(
            Uri.parse(""),
            "title",
            LocalDate.MIN,
            LocalDate.MIN.plusDays(3),
            listOf(LocalTime.parse("10:00"), LocalTime.parse("12:00")),
            152,
            "description"
        )
    }
}
