package woowacourse.movie.presenter.reservation

import android.content.Context
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import woowacourse.MockReservationTicketRepository
import woowacourse.movie.db.screening.ScreeningDao
import woowacourse.movie.db.screening.ScreeningDatabase
import woowacourse.movie.db.seats.SeatsDao
import woowacourse.movie.model.movie.Movie
import woowacourse.movie.model.seats.Grade
import woowacourse.movie.model.seats.Seat
import woowacourse.movie.model.seats.Seats

@ExtendWith(MockKExtension::class)
class SeatSelectionPresenterTest {
    @MockK
    private lateinit var view: SeatSelectionContract.View
    private lateinit var presenter: SeatSelectionContract.Presenter
    private lateinit var firstMovie: Movie
    private lateinit var context: Context

    @BeforeEach
    fun setUp() {
        context = mockk<Context>()
        presenter =
            SeatSelectionPresenter(
                view, SeatsDao(), ScreeningDao(),
                MockReservationTicketRepository(),
            )
        with(presenter) {
            manageSelectedSeats(true, 0, Seat('A', 1, Grade.B))
            manageSelectedSeats(true, 0, Seat('C', 1, Grade.S))
            manageSelectedSeats(true, 0, Seat('E', 1, Grade.A))
        }
        firstMovie = ScreeningDatabase.movies.first()
    }

    @Test
    fun `좌석 선택 화면으로 이동하면 총 20개의 좌석별 번호가 표시되어야 한다`() {
        every { view.initializeSeatsTable(any(), any()) } just runs
        presenter.initSeatNumbers()
        verify(exactly = 20) { view.initializeSeatsTable(any(), any()) }
    }

    @Test
    fun `좌석 선택 화면으로 이동하면 선택한 영화 제목이 화면에 표시되어야 한다`() {
        val expectedMovieTitle = "해리 포터와 마법사의 돌"
        every { view.showMovieTitle(firstMovie) } answers {
            val actualMovieTitle = arg<Movie>(0).title
            assertEquals(expectedMovieTitle, actualMovieTitle)
        }
        presenter.loadMovie(0)
        verify { view.showMovieTitle(firstMovie) }
    }

    @Test
    fun `좌석 금액이 25000인 상황에서 A좌석을 선택하면 총 결제 금액을 37000으로 업데이트하여 화면에 표시한다`() {
        val seats =
            Seats().apply {
                manageSelected(true, Seat('A', 1, Grade.B))
                manageSelected(true, Seat('C', 1, Grade.S))
            }
        val expectedPrice = seats.calculateAmount() + Grade.A.price

        every { view.showAmount(37_000) }.answers {
            val actualPrice = arg<Int>(0)
            assertEquals(expectedPrice, actualPrice)
        }
        presenter.updateTotalPrice(true, Seat('E', 1, Grade.A))
        verify { view.showAmount(37_000) }
    }

    @Test
    fun `확인 버튼을 누르면 예매 진행 여부를 묻는 다이얼로그를 띄워서 유저에게 알린다 `() {
        every { view.launchReservationConfirmDialog() } just runs
        presenter.initializeConfirmButton()
        verify { view.launchReservationConfirmDialog() }
    }

    @Test
    fun `화면 회전이 발생했을 때 선택되었던 좌석들을 복구하여 화면에 표시되어야 한다`() {
        val seats =
            Seats().apply {
                manageSelectedIndex(true, 0)
                manageSelectedIndex(true, 1)
                manageSelectedIndex(true, 2)
            }

        val expectedSeatIndex = listOf(0, 1, 2)

        every { view.restoreSelectedSeats(seats.seatsIndex) } answers {
            assertEquals(
                expectedSeatIndex, seats.seatsIndex,
            )
        }
        presenter.restoreSeats(seats, seats.seatsIndex)
        verify { view.restoreSelectedSeats(seats.seatsIndex) }
    }

    @Test
    fun `화면 회전이 발생했을 때 예약 상태를 복구하여 화면에 표시되어야 한다`() {
        val seats =
            Seats().apply {
                manageSelected(true, Seat('A', 1, Grade.B))
                manageSelected(true, Seat('C', 1, Grade.S))
                manageSelected(true, Seat('E', 1, Grade.A))
            }
        val expectedPrice = 37000

        every { view.setConfirmButtonEnabled(3) } just runs
        every { view.showAmount(seats.calculateAmount()) } answers {
            val actualPrice = arg<Int>(0)
            assertEquals(expectedPrice, actualPrice)
        }
        presenter.restoreReservation(3)
        verify { view.setConfirmButtonEnabled(any()) }
        verify { view.showAmount(seats.calculateAmount()) }
    }
}
