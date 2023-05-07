package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.data.model.MovieEntity
import java.time.LocalDate

@Parcelize
data class Movie(
    val poster: Int,
    val title: String,
    val movieSchedule: List<String>,
    val releaseDate: String,
    val runningTime: String,
    val synopsis: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) : Parcelable {
    companion object {
        fun from(movie: MovieEntity): Movie {
            return Movie(
                movie.poster,
                movie.title,
                movie.movieSchedule,
                movie.releaseDate,
                movie.runningTime,
                movie.synopsis,
                movie.startDate,
                movie.endDate
            )
        }
    }
}
