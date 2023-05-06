package woowacourse.movie.activity.seatselection

import android.os.Bundle
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
            reservation: ReservationViewData
        )

        fun setSeatsClick(row: Int, column: Int)
    }

    interface Presenter {

        fun initPrice(price: PriceViewData)

        fun setSeatsPrice(
            seats: SeatsViewData,
            reservationDetail: ReservationDetailViewData
        )

        fun reserveMovie(
            seats: SeatsViewData,
            movie: MovieViewData,
            reservationDetail: ReservationDetailViewData
        )

        fun saveSeat(bundle: Bundle)
        fun loadSeat(bundle: Bundle?)
    }
}
