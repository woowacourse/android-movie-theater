package woowacourse.movie.feature.reservationList

import com.example.domain.model.Money
import com.example.domain.model.Ticket
import com.example.domain.model.Tickets
import com.example.domain.model.seat.SeatPosition
import com.example.domain.repository.TicketsRepository
import com.example.domain.repository.dataSource.movieDataSources
import com.example.domain.repository.dataSource.theaterDataSources
import io.mockk.Runs
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Test
import woowacourse.movie.feature.reservationList.itemModel.TicketsItemModel
import woowacourse.movie.model.mapper.asPresentation
import java.time.LocalDateTime

internal class ReservationPresenterTest {
    private lateinit var view: ReservationListContract.View
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var ticketsRepository: TicketsRepository

    @Before
    fun init() {
        view = mockk()
        ticketsRepository = mockk()
        presenter = ReservationPresenter(view, ticketsRepository)
    }

    @Test
    fun 예약목록을_불러와서_뷰의_아이템을_업데이트한다() {
        every { ticketsRepository.allTickets() } returns mockTickets
        val slot = slot<List<TicketsItemModel>>()
        every { view.updateItems(capture(slot)) } just Runs

        // 실행
        presenter.loadTicketsItemList()

        val actual = slot.captured.map { it.ticketsState }
        val expected = mockTickets.map { it.asPresentation() }
        assert(actual == expected)
    }

    private val mockTickets = listOf<Tickets>(
        Tickets(
            listOf(
                Ticket(
                    theaterDataSources[0], movieDataSources[0], LocalDateTime.now(),
                    SeatPosition(1, 2), Money(10000)
                )
            )
        ),
        Tickets(
            listOf(
                Ticket(
                    theaterDataSources[0], movieDataSources[0], LocalDateTime.now(),
                    SeatPosition(1, 2), Money(10000)
                )
            )
        )
    )
}
