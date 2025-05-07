package woowacourse.movie.view.reservation.complete

import androidx.annotation.StringRes
import woowacourse.movie.model.ReservationInfo

interface ReservationCompleteContract {
    interface View {
        fun showErrorMessage(
            @StringRes messageResId: Int,
        )

        fun showReservationInfo(
            reservationInfo: ReservationInfo,
            seatLabels: List<String>,
        )

        fun finishView()
    }

    interface Presenter {
        fun fetchData(getReservationInfo: () -> ReservationInfo?)
    }
}
