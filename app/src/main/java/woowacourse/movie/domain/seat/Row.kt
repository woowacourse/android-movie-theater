package woowacourse.movie.domain.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Row(
    val value: Int,
) : Parcelable {
    init {
        require(value >= MINIMUM_ROW) { RowResult.OverOne }
    }

    companion object {
        private const val MINIMUM_ROW = 0
    }
}

sealed class RowResult {
    data class Success(val row: Row) : RowResult()
    object OverOne : RowResult()
}
