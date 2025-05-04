package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.SeatType

@Parcelize
enum class SeatTypeUiModel : Parcelable {
    S_CLASS,
    A_CLASS,
    B_CLASS,
}

fun SeatType.toUiModel(): SeatTypeUiModel = SeatTypeUiModel.valueOf(this.toString())

fun SeatTypeUiModel.toDomain(): SeatType = SeatType.valueOf(this.toString())
