package woowacourse.movie.model.theater

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.movie.Movie

@Parcelize
data class TheaterUIModel(
    val name: String,
    val movie: Movie,
    val timeSlotCount: Int,
) : Parcelable
