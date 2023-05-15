package woowacourse.movie.presentation.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import woowacourse.movie.data.model.TheaterEntity

@Parcelize
data class Theater(
    val name: String,
    val movies: List<Movie>
) : Parcelable {
    companion object {
        fun from(theaterEntity: TheaterEntity): Theater {
            return Theater(theaterEntity.name, theaterEntity.movies.map { Movie.from(it) })
        }
    }
}