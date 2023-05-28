package woowacourse.movie.presentation.movielist.selecttheater

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.data.storage.MovieTheaterStorage

class SelectTheaterPresenterTest {
    private lateinit var view: SelectTheaterContract.View
    private lateinit var movieTheaterStorage: MovieTheaterStorage
    private lateinit var presenter: SelectTheaterPresenter

    @Before
    fun initSelectTheaterPresenter() {
        view = mockk()
        movieTheaterStorage = mockk(relaxed = true)
    }

    @Test
    fun `해당영화가 상영중인지 조회해서 상영중이라면 상영 예매 리사이클러뷰를 표시한다`() {
        // given
        presenter = SelectTheaterPresenter(view, movieTheaterStorage, 1)
        every { movieTheaterStorage.getTheatersByMovieId(1) } returns listOf("강남", "선릉")
        val slot = slot<Boolean>()
        every { view.setViewByScreeningState(capture(slot)) } just Runs

        // when
        presenter.checkScreeningState()

        // then
        val actual = slot.captured
        assertEquals(true, actual)
        verify(exactly = 1) { view.setViewByScreeningState(true) }
    }
}
