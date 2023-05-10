package woowacourse.movie.presentation.bookedticketlist

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.bookedticket.BookedTicketsData
import woowacourse.movie.data.movie.MovieData
import woowacourse.movie.presentation.FakeMovieData
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime

class BookedTicketsPresenterTest {
    private lateinit var view: BookedTicketsContract.View
    private lateinit var presenter: BookedTicketsContract.Presenter
    private lateinit var bookedTicketsData: BookedTicketsData
    private lateinit var movieData: MovieData

    @Before
    fun `setUp`() {
        view = mockk()
        bookedTicketsData = mockk()
        movieData = mockk()
        presenter = BookedTicketsPresenter(view, bookedTicketsData, movieData)
    }

    @Test
    fun `영화 및 광고 아이템을 세팅한다`() {
        // given
        val ticketModelSlot = slot<List<TicketModel>>()
        every { view.setBookedTickets(capture(ticketModelSlot)) } just runs
        every { bookedTicketsData.getTickets() } returns listOf(
            TicketModel(
                1,
                "선릉",
                LocalDateTime.of(2024, 3, 1, 9, 0),
                3,
                33000,
                listOf("A1", "B1", "C1"),
            ),
        )
        // FakeMovieData 사용
        every { movieData.findMovieById(any()) } returns FakeMovieData.findMovieById(1L)

        // when
        presenter.requestBookedTickets()

        // then
        val expected = listOf(
            TicketModel(
                1,
                "선릉",
                LocalDateTime.of(2024, 3, 1, 9, 0),
                3,
                33000,
                listOf("A1", "B1", "C1"),
            ),
        )
        val actual = ticketModelSlot.captured
        assertEquals(expected, actual)
    }
}
