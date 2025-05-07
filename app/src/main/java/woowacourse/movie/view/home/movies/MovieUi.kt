package woowacourse.movie.view.home.movies

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.movietime.Date
import java.io.Serializable
import java.time.format.DateTimeFormatter

data class MovieUi(
    val movieId: Int,
    @DrawableRes val image: Int,
    val title: String,
    val date: Date,
    val time: Int,
) : Serializable {
    fun formattedDate(formatPattern: String): String {
        val formatter = DateTimeFormatter.ofPattern(formatPattern)
        val start = date.startDate.format(formatter)
        val end = date.endDate.format(formatter)
        return "$start ~ $end"
    }
}

fun Movie.toMovieUi(): MovieUi {
    return MovieUi(
        this.movieId,
        getDrawableResId(movieId),
        this.title,
        this.date,
        this.time,
    )
}

fun getMovieById(movieId: Int): Movie {
    return Movie.dummy.find { it.movieId == movieId }
        ?: throw NoSuchElementException()
}

private fun getDrawableResId(imageId: Int): Int {
    return when (imageId) {
        1 -> R.drawable.harry
        2 -> R.drawable.poster_suzume
        3 -> R.drawable.poster_castaway
        4 -> R.drawable.poster_singstreet
        5 -> R.drawable.poster_agugustrush
        6 -> R.drawable.poster_criminalcity
        else -> throw IllegalArgumentException()
    }
}
