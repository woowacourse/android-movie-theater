package woowacourse.movie.reservationList

import io.mockk.justRun
import io.mockk.mockk
import io.mockk.slot
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.model.MovieTicketModel
import woowacourse.movie.model.PeopleCountModel
import woowacourse.movie.model.PriceModel
import woowacourse.movie.model.TicketTimeModel
import woowacourse.movie.view.reservationList.ReservationListContract
import woowacourse.movie.view.reservationList.ReservationListPresenter
import java.time.LocalDateTime

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var view: ReservationListContract.View

    @Before
    fun setUp() {
        view = mockk()

        presenter = ReservationListPresenter(view)
    }

    @Test
    fun `예매 정보를 불러온다`() {
        // given
        val slot = slot<List<MovieTicketModel>>()
        justRun { view.setReservationView(capture(slot)) }
        presenter.setupReservations(listOf())

        // when
        presenter.loadReservations()

        // then
        val actual = slot.captured
        val expected = emptyList<MovieTicketModel>()
        assertEquals(expected, actual)
        verify { view.setReservationView(actual) }
    }

    @Test
    fun `예매한 영화 정보를 불러온다`() {
        // given
        val slot = slot<List<MovieTicketModel>>()
        justRun { view.setReservationView(capture(slot)) }
        presenter.setupReservations(listOf(dummyTicket))

        // when
        presenter.loadReservations()

        // then
        val actual = slot.captured
        assertEquals(dummyTicket, actual[0])
        verify { view.setReservationView(actual) }
    }

    companion object {
        private val dummyTicket = MovieTicketModel(
            "그레이의 50가지 그림자 1",
            TicketTimeModel(LocalDateTime.of(2023, 5, 1, 13, 0)),
            PeopleCountModel(2),
            emptySet(),
            PriceModel(0)
        )
    }
}
