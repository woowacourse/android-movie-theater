package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheaterState(
    val theaterId: Int,
    val theaterName: String
) : Parcelable
