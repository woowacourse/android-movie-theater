package woowacourse.movie.view

import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.fragment.reservationlist.ReservationListContract
import woowacourse.movie.fragment.reservationlist.ReservationListPresenter
import woowacourse.movie.view.data.ReservationsViewData

class ReservationListPresenterTest {
    private lateinit var view: ReservationListContract.View
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var reservationRepository: ReservationRepository
    private lateinit var reservation: Reservation

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        reservationRepository = mockk(relaxed = true)
        reservation = mockk(relaxed = true)
        every { reservationRepository.getData() } returns List(10) { reservation }
        presenter = ReservationListPresenter(view, reservationRepository)
    }

    @Test
    fun `저장된 예약 정보를 불러온다`() {
        // given
        val slotReservations = slot<ReservationsViewData>()
        every { view.setReservations(capture(slotReservations)) } answers { nothing }

        // when
        presenter.loadReservations()

        // then
        assertEquals(10, slotReservations.captured.value.size)
    }
}
