package woowacourse.movie.selectseat.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedSeatsUiModel(
    val seats: List<SeatUiModel> = emptyList(),
) : Parcelable
