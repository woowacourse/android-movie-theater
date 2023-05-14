package woowacourse.movie.theater.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class MovieTheaterDto(
    val place: String,
    val time: List<LocalTime>,
) : Parcelable
