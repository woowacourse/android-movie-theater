package woowacourse.movie.view.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTheater(
    val name: String,
    val screeningTimeslot: List<Int>
) : Parcelable
