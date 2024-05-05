package woowacourse.movie.selectseat.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.selectseat.uimodel.Position

@Parcelize
data class ParcelablePosition(val row: Int, val col: Int) : Parcelable {
    constructor(position: Position) : this(position.row, position.col)

    fun toUiModel(): Position = Position(row, col)
}
