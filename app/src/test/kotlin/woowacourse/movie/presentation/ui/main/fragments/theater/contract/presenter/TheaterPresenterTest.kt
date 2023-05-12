package woowacourse.movie.presentation.ui.main.fragments.theater.contract.presenter

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.presentation.model.movieitem.Movie
import woowacourse.movie.presentation.model.theater.PresentationTheater
import woowacourse.movie.presentation.ui.main.fragments.theater.contract.TheaterContract

internal class TheaterPresenterTest {
    private lateinit var presenter: TheaterContract.Presenter
    private lateinit var view: TheaterContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = TheaterPresenter(
            movie = mockk(relaxed = true),
            theaterRepository = mockk(relaxed = true)
        )
    }

    @Test
    internal fun 프레젠터에_뷰가_attach_될_때_극장_목록을_불러온다() {
        // given
        val movie: Movie = mockk()
        val movieId = 2
        every { movie.id } returns movieId

        val theaterRepository: TheaterRepository = mockk()
        val movieIdSlot = slot<Int>()
        every { theaterRepository.getAllByMovieId(capture(movieIdSlot)) } answers { listOf() }

        presenter = TheaterPresenter(movie, theaterRepository)

        // when
        presenter.attach(view)

        // then
        assertEquals(movieId, movieIdSlot.captured)
        verify(atLeast = 1) { view.showTheaterList(any()) }
    }

    @Test
    internal fun 극장을_선택하면_티켓팅_화면을_보여준다() {
        // given
        val theater: PresentationTheater = mockk(relaxed = true)
        presenter.attach(view)

        // when
        presenter.handleItem(theater)

        // then
        verify(exactly = 1) { view.showTicketingScreen(any(), theater) }
    }
}
