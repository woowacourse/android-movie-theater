package woowacourse.movie.view.home.movies

import androidx.annotation.DrawableRes
import woowacourse.movie.R
import woowacourse.movie.domain.Movie
import woowacourse.movie.domain.MovieId
import woowacourse.movie.domain.movietime.Date
import java.io.Serializable

data class MovieUi(
    val movieId: MovieId,
    @DrawableRes val image: Int,
    val title: String,
    val date: Date,
    val time: Int,
) : Serializable

fun Movie.toMovieUi(): MovieUi {
    return MovieUi(
        this.movieId,
        getDrawableResId(movieId),
        this.title,
        this.date,
        this.time,
    )
}

fun getMovieById(movieId: MovieId): Movie {
    return Movie.dummy.find { it.movieId == movieId }
        ?: throw NoSuchElementException()
}

private fun getDrawableResId(imageId: MovieId): Int {
    return when (imageId) {
        MovieId.Harry1 -> R.drawable.harry
        MovieId.Suzume -> R.drawable.poster_suzume
        MovieId.CastAway -> R.drawable.poster_castaway
        MovieId.StringStreet -> R.drawable.poster_singstreet
        MovieId.Agustrush -> R.drawable.poster_agugustrush
        MovieId.CriminalCity3 -> R.drawable.poster_criminalcity
    }
}
