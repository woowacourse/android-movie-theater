package woowacourse.movie.movielist.dto

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.movielist.ViewType
import woowacourse.movie.theater.dto.MovieTheatersDto
import java.time.LocalDate

@Parcelize
data class MovieDto(
    val viewType: ViewType = ViewType.MOVIE_VIEW,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val description: String,
    @DrawableRes val moviePoster: Int,
    val theaters: MovieTheatersDto,
) : Parcelable
