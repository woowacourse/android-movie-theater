package woowacourse.movie.data.database.movie

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.data.database.movie.MovieContentContract.TABLE_MOVIE_CONTENT

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
