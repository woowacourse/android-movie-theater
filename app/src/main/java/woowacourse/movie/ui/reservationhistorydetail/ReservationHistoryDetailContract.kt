package woowacourse.movie.ui.reservationhistorydetail

import woowacourse.movie.domain.model.Reservation
import java.time.LocalDate
import java.time.LocalTime

interface ReservationHistoryDetailContract {

    interface View {
        fun showReservation(
            reservation: Reservation,
            theaterName: String,
            screeningDate: LocalDate,
            screeningTime: LocalTime,
        )
    }

    interface Presenter {
        fun loadReservation(reservationId: Long)
    }
}