package woowacourse.movie.presentation.bookinghistory

import io.mockk.every
import io.mockk.invoke
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.bookinghistory.BookingHistoryRepository
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDateTime

class BookingHistoryPresenterTest {
    private lateinit var presenter: BookingHistoryPresenter
    private lateinit var view: BookingHistoryContract.View
    private lateinit var repository: BookingHistoryRepository

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        repository = mockk(relaxed = true)
        presenter = BookingHistoryPresenter(view, repository)
    }

    @Test
    fun `예매 내역을 가져와 화면에 출력한다`() {
        // Given
        val tickets = listOf<MovieTicket>()
        every {
            repository.getBookings(captureLambda())
        } answers {
            lambda<(List<MovieTicket>) -> Unit>().invoke(tickets)
        }

        // When
        presenter.loadBookingHistory()

        // Then
        verify { view.showBookingHistory(any()) }
    }

    @Test
    fun `예매 내역을 클릭하면 화면을 이동한다`() {
        // Given
        val ticket =
            MovieTicket(
                "TestMovie",
                "TestTheater",
                LocalDateTime.of(2025, 5, 12, 18, 0),
                1,
            )

        // When
        presenter.selectBookingHistory(ticket)

        // Then
        verify { view.navigateToBookingSummary(ticket) }
    }
}
