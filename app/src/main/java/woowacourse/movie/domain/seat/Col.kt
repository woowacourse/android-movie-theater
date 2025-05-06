package woowacourse.movie.domain.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Col(
    val value: Int,
) : Parcelable {
    init {
        require(value >= MINIMUM_COL) { ColResult.OverOne }
    }

    companion object {
        private const val MINIMUM_COL = 0
    }
}

sealed class ColResult {
    data class Success(val col: Col) : ColResult()
    object OverOne : ColResult()
}
