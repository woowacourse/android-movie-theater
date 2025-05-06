package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.Seat

@Parcelize
data class SeatUiModel(
    val label: String,
    val price: Int,
) : Parcelable

fun Seat.toPresentation(): SeatUiModel = SeatUiModel(label, price)
