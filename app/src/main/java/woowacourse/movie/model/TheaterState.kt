package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class TheaterState(
    val theaterName: String,
    val screenInfos: List<ScreenMovieInfo>
) : Parcelable
