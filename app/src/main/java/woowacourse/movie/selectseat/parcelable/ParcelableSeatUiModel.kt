package woowacourse.movie.selectseat.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.selectseat.uimodel.RateColor
import woowacourse.movie.selectseat.uimodel.SeatState
import woowacourse.movie.selectseat.uimodel.SeatUiModel

@Parcelize
data class ParcelableSeatUiModel(
    val showPosition: String,
    val rateColor: RateColor,
    val row: Int,
    val col: Int,
    val state: SeatState,
) : Parcelable {
    constructor(seatUiModel: SeatUiModel) : this(
        seatUiModel.showPosition,
        seatUiModel.rateColor,
        seatUiModel.row,
        seatUiModel.col,
        seatUiModel.state,
    )

    fun toUiModel(): SeatUiModel =
        SeatUiModel(
            showPosition,
            rateColor,
            row,
            col,
            state,
        )
}
