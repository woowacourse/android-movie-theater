package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.view.item.Movie

@Parcelize
data class TheaterUIModel(
    val name: String,
    val movie: Movie,
    val timeSlotCount: Int,
) : Parcelable
