package woowacourse.movie.model.ui.complete

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.database.MovieDatabase
import woowacourse.movie.data.database.ticket.TicketDao
import woowacourse.movie.ui.complete.MovieReservationCompleteContract
import woowacourse.movie.ui.complete.MovieReservationCompletePresenter

class MovieReservationCompletePresenterTest {
    private lateinit var presenter: MovieReservationCompletePresenter
    private lateinit var view: MovieReservationCompleteContract.View
    private lateinit var ticketDao: TicketDao
    private lateinit var db: MovieDatabase

    @BeforeEach
    fun setUp() {
        db =
            Room.inMemoryDatabaseBuilder(
                ApplicationProvider.getApplicationContext(),
                MovieDatabase::class.java,
            ).allowMainThreadQueries().build()
        ticketDao = db.ticketDao()
        view = mockk<MovieReservationCompleteContract.View>(relaxed = true)
        presenter = MovieReservationCompletePresenter(view, ticketDao)
//        UserTicketsImpl.save(
//            UserTicket(
//                title = "",
//                theater = "강남",
//                screeningStartDateTime = LocalDateTime.of(2024, 3, 28, 10, 0),
//                seatInformation = SeatInformation(1),
//            ),
//        )
    }

    @Test
    fun `티켓 정보가 없을 경우 에러를 보여준다`() {
        // given

        // when
        presenter.loadTicket(-1L)

        // then
        verify { view.showError(any()) }
    }

    @Test
    fun `에러를 처리한다`() {
        // given

        // when
        presenter.handleError(Throwable())

        // then
        verify { view.showError(any()) }
    }
}
