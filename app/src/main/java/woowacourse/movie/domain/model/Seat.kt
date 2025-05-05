package woowacourse.movie.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Seat(
    val row: Int,
    val column: Int,
    var isSelected: Boolean = false,
) : Parcelable {
    init {
        require(row in 0..MAX_ROW) { ERR_INVALID_ROW }
    }

    fun price() = SeatGrade.of(this).price

    companion object {
        private const val MAX_ROW = 4
        private const val ERR_INVALID_ROW = "올바르지 않은 행입니다"
    }
}
