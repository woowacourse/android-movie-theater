package woowacourse.movie.presentation.common.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.model.cinema.Seats

@Parcelize
class ScreenUiModel(
    val seats: List<SeatUiModel>,
) : Parcelable

fun Seats.toUiModel(): ScreenUiModel =
    ScreenUiModel(
        seats.map { it.toUiModel() },
    )
