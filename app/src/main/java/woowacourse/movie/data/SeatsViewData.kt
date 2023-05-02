package woowacourse.movie.data

import java.io.Serializable

data class SeatsViewData(
    val value: List<SeatViewData>
) : Serializable {
    companion object {
        const val SEATS_EXTRA_NAME = "seats"
    }
}
