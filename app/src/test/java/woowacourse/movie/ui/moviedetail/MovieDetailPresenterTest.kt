package woowacourse.movie.ui.moviedetail

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.uimodel.MovieListModel
import java.time.LocalDate

internal class MovieDetailPresenterTest {
    private lateinit var presenter: MovieDetailPresenter
    private lateinit var view: MovieDetailContract.View

    @Before
    fun setUp() {
        view = mockk()
        presenter = MovieDetailPresenter(view)
    }

    @Test
    fun PeopleCount가_1명일_때_add하면_2명이_된다() {
        val slot = slot<Int>()
        every { view.setPeopleCountView(capture(slot)) } answers { }
        presenter.updatePeopleCount(1)

        presenter.addCount()

        val actual = slot.captured
        assertEquals(2, actual)
        verify { view.setPeopleCountView(actual) }
    }

    @Test
    fun PeopleCount가_2명일_때_minus하면_1명이_된다() {
        val slot = slot<Int>()
        every { view.setPeopleCountView(capture(slot)) } answers { }
        presenter.updatePeopleCount(2)

        presenter.minusCount()

        val actual = slot.captured
        assertEquals(1, actual)
        verify { view.setPeopleCountView(actual) }
    }
}
