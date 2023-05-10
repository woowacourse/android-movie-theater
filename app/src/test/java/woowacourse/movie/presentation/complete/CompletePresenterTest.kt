package woowacourse.movie.presentation.complete

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.FakeMovieData

class CompletePresenterTest {
    private lateinit var view: CompleteContract.View
    private lateinit var presenter: CompleteContract.Presenter

    @Before
    fun `setUp`() {
        view = mockk()
        // FakeMovieData 사용
        presenter = CompletePresenter(FakeMovieData, view)
    }

    @Test
    fun `Id 가 1이면 해리포터 영화 제목을 세팅 한다`() {
        val movieTitleSlot = slot<String>()
        every { view.setMovieTitle(capture(movieTitleSlot)) } just runs

        presenter.setMovieTitle(1L)

        val expected = "해리 포터와 마법사의 돌"
        val actual = movieTitleSlot.captured

        assertEquals(expected, actual)
        verify { view.setMovieTitle(expected) }
    }
}
