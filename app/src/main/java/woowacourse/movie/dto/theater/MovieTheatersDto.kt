package woowacourse.movie.dto.theater

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.dto.theater.MovieTheaterDto

@Parcelize
data class MovieTheatersDto(
    val theaters: List<MovieTheaterDto>,
) : Parcelable
