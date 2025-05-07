package woowacourse.movie.view.movies.bottomsheet

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Result(
    val theaterName: String,
    val movieId: Int,
) : Parcelable
