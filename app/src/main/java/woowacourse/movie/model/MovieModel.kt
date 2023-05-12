package woowacourse.movie.model

import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.parcelize.Parcelize
import woowacourse.movie.domain.Movie
import java.time.LocalDate

fun MovieModel.mapToMovie(): Movie {
    return Movie(
        title,
        startDate,
        endDate,
        runningTime,
        description,
        theaters.map { it.mapToTheater() }
    )
}

@Parcelize
data class MovieModel(
    @DrawableRes
    val poster: Int,
    val title: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val runningTime: Int,
    val description: String,
    val theaters: List<TheaterModel>
) : Parcelable
