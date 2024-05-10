package woowacourse.movie.ui.reservation

import woowacourse.movie.domain.model.Reservation
import java.time.LocalDate
import java.time.LocalTime

interface ReservationContract {
    interface View {
        fun showReservation(
            reservation: Reservation,
            theaterName: String,
            screeningDate: LocalDate,
            screeningTime: LocalTime,
        )

        fun showToastMessage(message: String)

        fun showSnackBar(message: String)

        fun goToBack(message: String)

        fun unexpectedFinish(message: String)
    }

    interface Presenter {
        fun loadReservation(
            reservationId: Int,
            theaterId: Int,
        )

        fun saveReservation(
            reservationId: Int,
            theaterId: Int,
        )
    }
}
