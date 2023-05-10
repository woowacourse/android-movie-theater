package woowacourse.movie.presenter

import io.mockk.* // ktlint-disable no-wildcard-imports
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.TheatersUiModel
import woowacourse.movie.view.main.movieslist.MoviesContract
import woowacourse.movie.view.main.movieslist.MoviesPresenter

class MoviesPresenterTest {
    private lateinit var presenter: MoviesPresenter
    private lateinit var view: MoviesContract.View
    private lateinit var movieUiModel: MovieUiModel
    private lateinit var theatersUiModel: TheatersUiModel
    private lateinit var theaterUiModel: TheaterUiModel
    private lateinit var advertisementUiModel: AdvertisementUiModel

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        movieUiModel = mockk(relaxed = true)
        advertisementUiModel = mockk(relaxed = true)
        theatersUiModel = mockk(relaxed = true)
        theaterUiModel = mockk(relaxed = true)
        presenter = MoviesPresenter(view)
    }

    @Test
    fun 바텀시트를_클릭하면_영화화면으로_넘어간다() {
        // given
        every { movieUiModel.theaters } returns TheatersUiModel(listOf())
        every { view.showBottomSheet(any(), any()) } just runs
        every { view.showMovieReservationScreen(any(), any()) } just runs
        // when
        presenter.showPossibleTheatersBy(movieUiModel)
        presenter.startMovieReservation(movieUiModel, theaterUiModel)
        // then
        verify { view.showMovieReservationScreen(any(), any()) }
    }

    @Test
    fun 영화_목록을_클릭하면_바텀시트를_보여준다() {
        // given
        every { movieUiModel.theaters } returns TheatersUiModel(listOf())
        every { view.showBottomSheet(any(), any()) } just runs
        // when
        presenter.showPossibleTheatersBy(movieUiModel)
        // then
        verify { view.showBottomSheet(any(), any()) }
    }

    @Test
    fun 광고를_클릭하면_광고_url_페이지가_보여진다() {
        // given
        val slot = slot<String>()
        every { view.startUrl(capture(slot)) } answers { println("slot = ${slot.captured}") }
        every { advertisementUiModel.url } returns "www.naver.com"
        // when
        presenter.showAdvertisement(advertisementUiModel)
        // then
        val actual = slot.captured
        assertEquals("www.naver.com", actual)
        every { view.startUrl(actual) }
    }
}
