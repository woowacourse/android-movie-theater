package woowacourse.movie.selectseat.parcelable

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class SeatsInstanceState(
    val seatMap: Map<ParcelablePosition, ParcelableSeatUiModel>,
) : Parcelable
