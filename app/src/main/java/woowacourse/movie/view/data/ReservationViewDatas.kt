package woowacourse.movie.view.data

import java.io.Serializable

data class ReservationViewDatas(val value: List<ReservationViewData>) : Serializable {
    companion object {
        const val RESERVATION_VIEW_DATAS_EXTRA_NAME = "reservationViewDatas"
    }
}
