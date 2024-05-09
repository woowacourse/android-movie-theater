package woowacourse.movie.feature.setting

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import woowacourse.movie.data.ticket.FakeTicketRepository
import woowacourse.movie.data.ticket.TicketRepository
import woowacourse.movie.data.ticket.entity.Ticket
import woowacourse.movie.feature.movieId
import woowacourse.movie.feature.screeningDate
import woowacourse.movie.feature.screeningTime
import woowacourse.movie.feature.selectedSeats
import woowacourse.movie.feature.theaterName
import woowacourse.movie.feature.ticket

class SettingPresenterTest {
    private lateinit var view: SettingContract.View
    private lateinit var ticketRepository: TicketRepository
    private lateinit var presenter: SettingContract.Presenter
    private val ticketCount = 3

    @BeforeEach
    fun setUp() {
        view = mockk()
        ticketRepository = FakeTicketRepository()
        presenter = SettingPresenter(view)
        repeat(ticketCount) {
            ticketRepository.save(
                movieId,
                screeningDate,
                screeningTime,
                selectedSeats,
                theaterName,
            )
        }
    }

    @Test
    fun `예매한 티켓들의 알림을 설정한다`() {
        // given
        every { view.setTicketAlarm(any()) } just runs

        // when
        presenter.setTicketsAlarm(ticketRepository)

        // then
        verify { view.setTicketAlarm(ticket.copy(id = 0L)) }
        verify { view.setTicketAlarm(ticket.copy(id = 1L)) }
        verify { view.setTicketAlarm(ticket.copy(id = 2L)) }
    }

    @Test
    fun `예매한 티켓들의 알림을 취소한다`() {
        // given
        every { view.cancelTicketAlarm(any()) } just runs

        // when
        presenter.cancelTicketsAlarm(ticketRepository)

        // then
        verify { view.cancelTicketAlarm(ticket.copy(id = 0L)) }
        verify { view.cancelTicketAlarm(ticket.copy(id = 1L)) }
        verify { view.cancelTicketAlarm(ticket.copy(id = 2L)) }
    }
}
