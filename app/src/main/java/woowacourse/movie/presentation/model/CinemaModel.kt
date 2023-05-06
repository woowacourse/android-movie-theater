package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class CinemaModel(
    val cinemaName: String,
    val movieId: Long,
    val movieTimes: List<LocalTime>,
) : Parcelable
