package woowacourse.movie.ui.main.cinemaListBottomSheet

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.Test
import woowacourse.movie.data.CinemaRepository
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListContract
import woowacourse.movie.ui.main.cinemaBottomSheet.CinemaListPresenter

class CinemaListPresenterTest {
    @Test
    fun `영화관 리스트를 가져올 수 있다`() {
        // given
        val view: CinemaListContract.View = mockk(relaxed = true)
        val cinemaRepository: CinemaRepository = mockk(relaxed = true)
        val presenter = CinemaListPresenter(view, cinemaRepository)
        every { cinemaRepository.allCinema() } returns listOf()

        // when
        presenter.getCinemaList()

        // then
        verify { cinemaRepository.allCinema() }
        verify { view.setAdapter(listOf()) }
    }
}
