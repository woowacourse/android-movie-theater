package woowacourse.movie.presenter

import domain.Theaters
import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.MoviesContract
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel
import woowacourse.movie.model.TheaterUiModel
import woowacourse.movie.model.TheatersUiModel

class MoviesPresenterTest {
    private lateinit var presenter: MoviesPresenter
    private lateinit var view: MoviesContract.View
    private lateinit var movieUiModel: MovieUiModel
    private lateinit var theatersUiModel: TheatersUiModel
    private lateinit var theaterUiModel: TheaterUiModel
    private lateinit var advertisementUiModel: AdvertisementUiModel

    @Before
    fun setUp() {
        view = mockk()
        movieUiModel = mockk()
        advertisementUiModel = mockk()
        theatersUiModel = mockk()
        theaterUiModel = mockk()
        presenter = MoviesPresenter(view)
    }

    @Test
    fun 바텀시트를_클릭하면_영화화면으로_넘어간다() {
        // given
        every { movieUiModel.theaters } returns TheatersUiModel(listOf())
        every { view.showBottomSheet(any()) } just runs
        every { view.startMovieReservationActivity(any(), any()) } just runs
        // when
        presenter.onMovieItemClick(movieUiModel)
        presenter.onTheaterItemClick(theaterUiModel)
        // then
        verify { view.startMovieReservationActivity(any(), any()) }
    }

    @Test
    fun 영화_목록을_클릭하면_바텀시트를_보여준다() {
        // given
        every { movieUiModel.theaters } returns TheatersUiModel(listOf())
        every { view.showBottomSheet(any()) } just runs
        // when
        presenter.onMovieItemClick(movieUiModel)
        // then
        verify { view.showBottomSheet(any()) }
    }

    @Test
    fun 광고를_클릭하면_광고_url_페이지가_보여진다() {
        // given
        val slot = slot<String>()
        every { view.startAdvertisementUrl(capture(slot)) } answers { println("slot = ${slot.captured}") }
        every { advertisementUiModel.url } returns "www.naver.com"
        // when
        presenter.onAdvertisementItemClick(advertisementUiModel)
        // then
        val actual = slot.captured
        assertEquals("www.naver.com", actual)
        every { view.startAdvertisementUrl(actual) }
    }
}
