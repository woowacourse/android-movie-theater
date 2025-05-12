package woowacourse.movie.presentation.bookinghistory

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.bookinghistory.BookingHistoryDao
import woowacourse.movie.data.bookinghistory.MovieDatabase
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDateTime

class BookingHistoryPresenterTest {
    private lateinit var presenter: BookingHistoryPresenter
    private lateinit var view: BookingHistoryContract.View
    private lateinit var database: MovieDatabase
    private lateinit var dao: BookingHistoryDao

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        database = mockk()
        dao = mockk()
        every { database.bookingHistoryDao() } returns dao
        presenter = BookingHistoryPresenter(view, database)
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
