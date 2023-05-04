package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class ScreenMovieInfo(
    val movieState: MovieState,
    val dateTimes: List<LocalTime>
) : Parcelable
