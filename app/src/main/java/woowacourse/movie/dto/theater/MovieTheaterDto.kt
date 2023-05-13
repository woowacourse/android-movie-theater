package woowacourse.movie.dto.theater

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalTime

@Parcelize
data class MovieTheaterDto(
    val place: String,
    val time: List<LocalTime>,
) : Parcelable
