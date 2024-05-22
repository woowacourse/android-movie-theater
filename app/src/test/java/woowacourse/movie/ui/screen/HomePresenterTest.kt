package woowacourse.movie.ui.screen

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.repository.DefaultAdvertisementRepository
import woowacourse.movie.data.repository.DefaultMovieRepository
import woowacourse.movie.data.repository.DefaultScreenRepository
import woowacourse.movie.data.repository.FakeAdvertisementDataSource
import woowacourse.movie.data.repository.FakeMovieDataSource
import woowacourse.movie.data.repository.FakeScreenDataSource
import woowacourse.movie.domain.model.DateRange
import woowacourse.movie.domain.model.Image
import woowacourse.movie.domain.usecase.ScreensAndAdvertisementUseCase
import woowacourse.movie.ui.MoviePreviewUI
import woowacourse.movie.ui.ScreenAd
import woowacourse.movie.ui.home.HomeContract
import woowacourse.movie.ui.home.HomePresenter
import java.time.LocalDate

class HomePresenterTest {
    private lateinit var view: HomeContract.View
    private lateinit var presenter: HomeContract.Presenter

    @BeforeEach
    fun setUp() {
        view = mockk<HomeContract.View>(relaxed = true)
        val screenRepository =
            DefaultScreenRepository(FakeScreenDataSource(), DefaultMovieRepository(FakeMovieDataSource()))
        presenter =
            HomePresenter(
                view = view,
                screenRepository = screenRepository,
                screensAndAdvertisementUseCase =
                    ScreensAndAdvertisementUseCase(
                        screenRepository = screenRepository,
                        advertisementRepository = DefaultAdvertisementRepository(FakeAdvertisementDataSource()),
                    ),
            )
    }

    @Test
    fun `영화와 광고를 보여준다`() {
        // given & when
        presenter.loadScreen()

        verify {
            view.showScreens(
                listOf(
                    ScreenAd.ScreenPreviewUi(
                        1,
                        MoviePreviewUI(
                            "title1",
                            1,
                            Image.StringImage("1"),
                        ),
                        DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
                    ),
                    ScreenAd.ScreenPreviewUi(
                        2,
                        MoviePreviewUI(
                            "title2",
                            2,
                            Image.StringImage("2"),
                        ),
                        DateRange(LocalDate.of(2024, 3, 2), LocalDate.of(2024, 3, 4)),
                    ),
                    ScreenAd.ScreenPreviewUi(
                        3,
                        MoviePreviewUI(
                            "title3",
                            3,
                            Image.StringImage("3"),
                        ),
                        DateRange(LocalDate.of(2024, 3, 3), LocalDate.of(2024, 3, 5)),
                    ),
                    ScreenAd.Advertisement(
                        0,
                        Image.DrawableImage(1),
                    ),
                ),
            )
        }
    }

    @Test
    fun `영화를 상영하는 상영관들을 보여준다`() {
        presenter.loadTheaters(1)
        verify {
            view.showTheatersScreeningMovie(
                ScreenAd.ScreenPreviewUi(
                    1,
                    MoviePreviewUI(
                        "title1",
                        1,
                        Image.StringImage("1"),
                    ),
                    DateRange(LocalDate.of(2024, 3, 1), LocalDate.of(2024, 3, 3)),
                ),
            )
        }
    }
}
