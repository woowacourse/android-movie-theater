package woowacourse.movie.presentation.bookedticketlist

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import woowacourse.movie.data.bookedticket.BookedTicketsData
import woowacourse.movie.presentation.FakeMovieData
import woowacourse.movie.presentation.model.TicketModel
import java.time.LocalDateTime

class BookedTicketsPresenterTest {
    private lateinit var view: BookedTicketsContract.View
    private lateinit var presenter: BookedTicketsContract.Presenter

    private object FakeBookedTicketsData : BookedTicketsData {
        override fun getTickets(): List<TicketModel> = listOf(
            TicketModel(
                1,
                "선릉",
                LocalDateTime.of(2024, 3, 1, 9, 0),
                3,
                33000,
                listOf("A1", "B1", "C1"),
            ),
        )

        override fun addTickets(ticketModel: TicketModel) {}
    }

    @Before
    fun `setUp`() {
        view = mockk()
        // FakeMovieData 사용
        presenter = BookedTicketsPresenter(view, FakeBookedTicketsData, FakeMovieData)
    }

    @Test
    fun `영화 및 광고 아이템을 세팅한다`() {
        // given
        val ticketModelSlot = slot<List<TicketModel>>()
        every { view.setBookedTicketsAdapter(capture(ticketModelSlot)) } just runs

        // when
        presenter.setBookedTickets()

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
        Assert.assertEquals(expected, actual)
    }
}
