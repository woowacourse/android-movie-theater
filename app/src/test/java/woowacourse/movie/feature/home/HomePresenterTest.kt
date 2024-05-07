package woowacourse.movie.feature.home

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.movie.TestFixture.mockAdvertisements
import woowacourse.movie.TestFixture.mockMovies
import woowacourse.movie.db.advertisement.AdvertisementDao
import woowacourse.movie.db.screening.ScreeningDao

@ExtendWith(MockKExtension::class)
class HomePresenterTest {
    @MockK
    private lateinit var view: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @BeforeEach
    fun setUp() {
        presenter = HomePresenter(view, ScreeningDao(), AdvertisementDao())
    }

    @Test
    fun `영화와_광고_데이터를_불러온다`() {
        every { view.showMovieCatalog(any(), any()) } just runs
        presenter.loadMovieCatalog()
        verify { view.showMovieCatalog(mockMovies, mockAdvertisements) }
    }

    @Test
    fun `영화_목록의_지금_예매_버튼을_누르면_선택된_영화의_ID를_가지고_극장_선택으로_이동한다`() {
        every { view.navigateToTheaterSelection(any()) } just runs
        presenter.sendMovieIdToTheaterSelection(
            movieId = 0,
        )
        verify { view.navigateToTheaterSelection(0) }
    }
}
