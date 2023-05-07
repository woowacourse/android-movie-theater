package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class ScreeningMovieState(
    val movie: MovieState,
    val screeningDateTimes: List<LocalTime>
) : Parcelable
