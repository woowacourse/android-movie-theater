package woowacourse.movie.reservationresult

import android.content.Context
import woowacourse.movie.reservationresult.uimodel.ReservationResultUiModel

interface ReservationResultContract {
    interface View {
        fun showResult(reservationResult: ReservationResultUiModel)
    }

    interface Presenter {
        fun loadReservationResult(reservationId: Long)

        fun setAlarm(
            reservationId: Long,
            context: Context,
        )
    }
}
