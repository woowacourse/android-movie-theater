package woowacourse.movie.presenter.seat

import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.domain.model.SeatFactory
import woowacourse.movie.domain.model.Ticket
import woowacourse.movie.fixture.TestData
import woowacourse.movie.repository.Repository
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionContract
import woowacourse.movie.view.movies.reservation.seat.SeatSelectionPresenter

class SeatSelectionPresenterTest {
    private lateinit var presenter: SeatSelectionPresenter
    private lateinit var view: SeatSelectionContract.View
    private lateinit var repository: Repository<Ticket>

    @BeforeEach
    fun setUp() {
        view = mockk()
        repository = mockk()
        presenter = SeatSelectionPresenter(view, repository)
    }

    @Test
    fun `예매를 진행하면 데이터베이스에 예매 정보를 저장한다`() {
        // given
        val ticket: Ticket = mockk()
        every { repository.save(ticket) } returns Result.success(Unit)
        every { view.navigateToResult(ticket) } just Runs
        every { view.showSeats(SeatFactory.default().seats, ticket.seats) } just Runs
        every { view.updateTicketInfo(ticket) } just Runs
        presenter.loadSeats(TestData.reservationInfo, ticket)

        // when
        presenter.completeReservation()

        // then
        verify { repository.save(ticket) }
    }
}
