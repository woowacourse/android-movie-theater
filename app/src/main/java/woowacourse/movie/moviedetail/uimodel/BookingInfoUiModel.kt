package woowacourse.movie.moviedetail.uimodel

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class BookingInfoUiModel(
    val screeningId: Long,
    val count: HeadCountUiModel,
) : Parcelable
