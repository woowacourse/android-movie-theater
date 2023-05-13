package woowacourse.movie.theater.dto

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class MovieTheatersDto(
    val theaters: List<MovieTheaterDto>,
) : Parcelable
