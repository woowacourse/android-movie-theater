package woowacourse.movie.activity.reservationresult

import woowacourse.movie.view.data.ReservationViewData

interface ReservationResultContract {
    interface View {
        var presenter: Presenter
        fun renderReservation(date: String, peopleCount: String, price: String)
    }

    interface Presenter {
        fun renderReservation(
            reservationViewData: ReservationViewData
        )
    }
}
