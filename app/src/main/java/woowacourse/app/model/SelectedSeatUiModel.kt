package woowacourse.app.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.app.model.ticket.SeatUiModel

@Parcelize
data class SelectedSeatUiModel(val selectedSeat: Set<SeatUiModel>) : Parcelable
