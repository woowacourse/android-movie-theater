package woowacourse.movie.dto.theater

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTheatersDto(
    val theaters: List<MovieTheaterDto>,
) : Parcelable
