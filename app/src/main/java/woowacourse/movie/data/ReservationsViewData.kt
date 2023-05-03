package woowacourse.movie.data

import java.io.Serializable

data class ReservationsViewData(val value: List<ReservationViewData>) : Serializable {
    companion object {
        const val RESERVATIONS_EXTRA_NAME = "reservationsViewData"
    }
}
