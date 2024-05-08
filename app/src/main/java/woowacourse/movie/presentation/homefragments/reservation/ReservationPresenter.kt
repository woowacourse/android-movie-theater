package woowacourse.movie.presentation.homefragments.reservation

import woowacourse.movie.model.Reservation
import woowacourse.movie.model.Seat

class ReservationPresenter(private val view: ReservationContract.View) :
    ReservationContract.Presenter {
    private val dummy =
        listOf(
            Reservation(
                "영화 제목 1",
                "2024.01.01",
                "17:00",
                listOf(Seat(0, 0), Seat(0, 1)),
                "극장 이름 1",
            ),
            Reservation(
                "영화 제목 2",
                "2024.07.10",
                "17:00",
                listOf(Seat(1, 1), Seat(0, 1)),
                "극장 이름 2",
            ),
        )

    override fun loadReservations() {
        view.displayReservations(dummy)
    }
}
