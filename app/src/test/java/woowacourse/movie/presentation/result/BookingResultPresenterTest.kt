package woowacourse.movie.presentation.result

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.movie.MovieTicket
import java.time.LocalDateTime

class BookingResultPresenterTest {
    private lateinit var view: BookingResultContract.View
    private lateinit var presenter: BookingResultContract.Presenter

    private val testTicket =
        MovieTicket(
            movieTitle = "test",
            theaterName = "선릉 극장",
            showtime = LocalDateTime.of(2025, 12, 31, 12, 0),
            headCount = 2,
        )

    @BeforeEach
    fun setUp() {
        view = mockk(relaxed = true)
        presenter = BookingResultPresenter(view, testTicket)
    }

    @Test
    fun `티켓의 정보가 출력된다`() {
        // When
        presenter.loadBookingResult()

        // Then
        verify { view.showTicketInfo(testTicket) }
    }
}
