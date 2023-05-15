package woowacourse.app.presentation.model.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class SelectedSeatUiModel(val selectedSeat: Set<SeatUiModel>) : Parcelable
