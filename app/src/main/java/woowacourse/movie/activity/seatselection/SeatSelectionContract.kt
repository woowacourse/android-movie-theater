package woowacourse.movie.activity.seatselection

import woowacourse.movie.view.data.MovieViewData
import woowacourse.movie.view.data.PriceViewData
import woowacourse.movie.view.data.ReservationDetailViewData
import woowacourse.movie.view.data.ReservationViewData
import woowacourse.movie.view.data.SeatsViewData
import java.time.LocalDateTime

interface SeatSelectionContract {

    interface View {
        var presenter: Presenter
        fun setPriceView(price: String)
        fun makeAlarm(alarmDate: LocalDateTime, reservation: ReservationViewData)
        fun startReservationResultActivity(
            reservation: ReservationViewData,
        )
    }

    interface Presenter {

        fun initPrice(price: PriceViewData)

        fun updateSeatsPrice(
            seats: SeatsViewData,
            reservationDetail: ReservationDetailViewData,
        )

        fun reserveMovie(
            seats: SeatsViewData,
            movie: MovieViewData,
            reservationDetail: ReservationDetailViewData,
        )
    }
}
