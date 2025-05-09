package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.screen.SeatType

@Parcelize
enum class SeatTypeUiModel : Parcelable {
    S_CLASS,
    A_CLASS,
    B_CLASS,
}

fun SeatType.toUiModel(): SeatTypeUiModel =
    when (this) {
        SeatType.S_CLASS -> SeatTypeUiModel.S_CLASS
        SeatType.A_CLASS -> SeatTypeUiModel.A_CLASS
        SeatType.B_CLASS -> SeatTypeUiModel.B_CLASS
    }

fun SeatTypeUiModel.toModel(): SeatType =
    when (this) {
        SeatTypeUiModel.S_CLASS -> SeatType.S_CLASS
        SeatTypeUiModel.A_CLASS -> SeatType.A_CLASS
        SeatTypeUiModel.B_CLASS -> SeatType.B_CLASS
    }
