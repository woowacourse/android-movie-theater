package woowacourse.movie.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class SelectTheaterAndMovieState(
    val theater: TheaterState,
    val movie: MovieState,
    val allowTimes: List<LocalTime>
) : Parcelable
