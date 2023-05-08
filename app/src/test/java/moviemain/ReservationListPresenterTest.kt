package moviemain

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.slot
import io.mockk.verify
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import woowacourse.movie.domain.price.Price
import woowacourse.movie.domain.repository.ReservationRepository
import woowacourse.movie.domain.reservation.Reservation
import woowacourse.movie.domain.system.Seat
import woowacourse.movie.view.model.ReservationUiModel
import woowacourse.movie.view.moviemain.reservationlist.ReservationListContract
import woowacourse.movie.view.moviemain.reservationlist.ReservationListPresenter
import java.time.LocalDateTime

class ReservationListPresenterTest {
    private lateinit var presenter: ReservationListContract.Presenter
    private lateinit var view: ReservationListContract.View

    @Before
    fun setUp() {
        view = mockk(relaxed = true)
        val reservationRepository = object : ReservationRepository {
            override fun add(reservation: Reservation) {
            }

            override fun findAll(): List<Reservation> {
                return listOf(
                    Reservation(
                        "해리 포터와 마법사의 돌",
                        LocalDateTime.of(2024, 4, 1, 12, 0),
                        listOf(Seat(1, 1)),
                        Price(10000),
                        "선릉 극장",
                    ),
                )
            }
        }

        presenter = ReservationListPresenter(view, reservationRepository)
    }

    @Test
    fun 예매_정보를_UiModel로_가져와_띄울_수_있다() {
        val slot = slot<List<ReservationUiModel>>()
        every { view.showReservations(capture(slot)) } just runs
        presenter.fetchReservations()
        val expected = listOf(
            ReservationUiModel(
                "해리 포터와 마법사의 돌",
                LocalDateTime.of(2024, 4, 1, 12, 0),
                1,
                listOf("B2"),
                10000,
                "선릉 극장",
            ),
        )
        assertEquals(expected, slot.captured)
        verify { view.showReservations(expected) }
    }
}
