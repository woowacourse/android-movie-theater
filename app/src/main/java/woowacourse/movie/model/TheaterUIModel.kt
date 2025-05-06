package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheaterUIModel(
    val name: String,
    val movie: Movie,
    val timeSlotCount: Int,
) : Parcelable
