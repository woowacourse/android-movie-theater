package woowacourse.movie.presenter.result

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.MockReservationTicketRepository
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.screening.ScreeningDatabase
import woowacourse.movie.db.theater.TheaterDao
import woowacourse.movie.db.theater.TheaterDatabase
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.movie.ScreeningDateTime
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.SeatSelection
import woowacourse.movie.model.seats.TheaterSeat
import woowacourse.movie.model.theater.Theater
import woowacourse.movie.model.ticket.Ticket

@ExtendWith(MockKExtension::class)
class ReservationResultPresenterTest {
    @MockK
    private lateinit var view: ReservationResultContract.View
    private lateinit var presenter: ReservationResultContract.Presenter
    private lateinit var firstMovie: Movie
    private lateinit var sampleTicket: Ticket
    private lateinit var firstTheater: Theater

    @BeforeEach
    fun setUp() {
        presenter = ReservationResultPresenter(view, MockReservationTicketRepository(), ScreeningDao(), TheaterDao())
        firstMovie = ScreeningDatabase.movies.first()
        firstTheater = TheaterDatabase.theaters.first()
        val seatSelection = SeatSelection()
        seatSelection.manageSelected(true, TheaterSeat('A', 1, Grade.A))
        sampleTicket = Ticket(0, 0, seatSelection, ScreeningDateTime("", ""), 12000)
    }

    @Test
    fun `영화 예매를 완료하면 예매한 영화 제목이 화면에 표시되어야 한다`() {
        val expectedMovieTitle = "해리 포터와 마법사의 돌"

        every { view.showReservationMovieTitle(firstMovie) } answers {
            val actualMovie = arg<Movie>(0)
            assertEquals(actualMovie.title, expectedMovieTitle)
        }
        presenter.loadMovie(0)
        verify { view.showReservationMovieTitle(firstMovie) }
    }

    @Test
    fun `영화 예매를 완료하면 예매한 티켓 정보가 화면에 표시되어야 한다`() {
        val expectedNumberOfTicket = 1
        val expectedTicketPrice = 12000
        val expectedSeatRow = 'A'
        val expectedSeatColumn = 1

        every { view.showReservationTicketInfo(sampleTicket) } answers {
            val actualTicket = arg<Ticket>(0)
            assertEquals(actualTicket.amount, expectedTicketPrice)
            assertEquals(actualTicket.seatSelection.theaterSeats.size, expectedNumberOfTicket)
            assertEquals(actualTicket.seatSelection.theaterSeats.first().row, expectedSeatRow)
            assertEquals(actualTicket.seatSelection.theaterSeats.first().column, expectedSeatColumn)
        }
        presenter.loadTicket(sampleTicket)
        verify { view.showReservationTicketInfo(sampleTicket) }
    }

    @Test
    fun `영화 예매를 완료하면 예매한 극장 이름이 화면에 표시되어야 한다`() {
        val expectedTheaterName = "선릉 극장"

        every { view.showReservationTheaterName(firstTheater.theaterName) } answers {
            val actualTheaterName = arg<String>(0)
            assertEquals(actualTheaterName, expectedTheaterName)
        }
        presenter.loadTheater(0)
        verify { view.showReservationTheaterName(firstTheater.theaterName) }
    }
}
