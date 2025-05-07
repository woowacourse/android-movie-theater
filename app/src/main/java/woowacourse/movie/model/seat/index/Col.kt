package woowacourse.movie.model.seat.index

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
@JvmInline
value class Col(
    val index: Int,
) : Parcelable
