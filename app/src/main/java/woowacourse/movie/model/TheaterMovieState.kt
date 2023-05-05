package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class TheaterMovieState(
    val theaterName: String,
    val movie: MovieState,
    val times: List<LocalTime>
) : Parcelable
