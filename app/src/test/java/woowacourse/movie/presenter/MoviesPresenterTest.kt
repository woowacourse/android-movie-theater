package woowacourse.movie.presenter

import io.mockk.*
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.contract.MoviesContract
import woowacourse.movie.model.AdvertisementUiModel
import woowacourse.movie.model.MovieUiModel

class MoviesPresenterTest {
    private lateinit var presenter: MoviesPresenter
    private lateinit var view: MoviesContract.View
    private lateinit var movieUiModel: MovieUiModel
    private lateinit var advertisementUiModel: AdvertisementUiModel

    @Before
    fun setUp() {
        view = mockk()
        movieUiModel = mockk()
        advertisementUiModel = mockk()
        presenter = MoviesPresenter(view)
    }

    @Test
    fun 영화_목록을_클릭하면_예매화면으로_넘어간다() {
        // given
        every {
            view.startMovieReservationActivity(movieUiModel)
        } just runs
        // when
        presenter.onMovieItemClick(movieUiModel)
        // then
        verify { view.startMovieReservationActivity(movieUiModel) }
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
