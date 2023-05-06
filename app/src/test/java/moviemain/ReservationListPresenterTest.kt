package moviemain

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

    @Before
    fun setUp() {
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

        presenter = ReservationListPresenter(reservationRepository)
    }

    @Test
    fun 예매_정보를_UiModel로_가져올_수_있다() {
        val reservationUiModel = presenter.getReservations()[0]
        val expected = ReservationUiModel(
            "해리 포터와 마법사의 돌",
            LocalDateTime.of(2024, 4, 1, 12, 0),
            1,
            listOf("B2"),
            10000,
            "선릉 극장",
        )
        assertEquals(expected, reservationUiModel)
    }
}
