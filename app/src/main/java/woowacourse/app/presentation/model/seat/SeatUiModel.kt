package woowacourse.app.presentation.model.seat

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.domain.ticket.SeatRank

@Parcelize
data class SeatUiModel(
    val rank: SeatRank,
    val position: PositionUiModel,
) : Parcelable
