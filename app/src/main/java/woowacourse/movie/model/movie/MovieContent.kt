package woowacourse.movie.model.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.movie.MovieContentContract.TABLE_MOVIE_CONTENT
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class MovieContent(
    val id: Long = 0,
    val imageId: String,
    val title: String,
    val openingMovieDate: LocalDate,
    val endingMoviesDate: LocalDate,
    val runningTime: Int,
    val synopsis: String,
    val theaterIds: List<Long>,
) {
    fun getDatesInRange(): List<LocalDate> {
        val numberOfDays = ChronoUnit.DAYS.between(openingMovieDate, endingMoviesDate) + OFFSET
        return List(numberOfDays.toInt()) { index -> openingMovieDate.plusDays(index.toLong()) }
    }

    companion object {
        private const val OFFSET = 1
    }
}

@Entity(tableName = TABLE_MOVIE_CONTENT)
data class MovieContentEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val imageId: String,
    val title: String,
    val openingMovieDate: String,
    val endingMoviesDate: String,
    val runningTime: Int,
    val synopsis: String,
    val theaterIds: List<Long>,
)

fun MovieContentEntity.toMovieContent(): MovieContent =
    MovieContent(
        id = id,
        imageId = imageId,
        title = title,
        openingMovieDate = LocalDate.parse(openingMovieDate),
        endingMoviesDate = LocalDate.parse(endingMoviesDate),
        runningTime = runningTime,
        synopsis = synopsis,
        theaterIds = theaterIds,
    )
