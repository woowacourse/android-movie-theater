package woowacourse.movie.movie.history

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.dto.BookingHistoryDto
import woowacourse.movie.dto.movie.BookingMovieEntity
import woowacourse.movie.dto.movie.MovieDateDto
import woowacourse.movie.dto.movie.MovieTimeDto
import woowacourse.movie.dto.ticket.TicketCountDto
import woowacourse.movie.history.HistoryContract
import woowacourse.movie.history.HistoryPresenter
import java.time.LocalDate
import java.time.LocalTime

internal class HistoryPresenterTest {

    private lateinit var view: HistoryContract.View
    private lateinit var presenter: HistoryContract.Presenter

    private val mockHistory = listOf(
        BookingMovieEntity(
            title = "Joker",
            date = MovieDateDto(LocalDate.of(2022, 5, 15)),
            time = MovieTimeDto(LocalTime.of(14, 30)),
            theaterName = "Megabox",
            price = 10000,
            seats = "A1, A2",
            ticketCount = TicketCountDto(2)
        )
    )

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = HistoryPresenter(view)
    }

    @Test
    fun `Presenter가 사용자가 그동안 예매했던 기록들을 잘 가져온다`() {
        val slot = slot<List<BookingMovieEntity>>()
        val bookingHistoryDto = mockk<BookingHistoryDto>()
        every { bookingHistoryDto.getHistory() } returns mockHistory
        every { view.setUpHistoryData(capture(slot)) } answers { nothing }

        // When
        presenter.initFragment()

        // Then
        val expected = mockHistory
        assertEquals(expected, slot.captured)
        verify { view.setUpHistoryData(mockHistory) }
    }
}
