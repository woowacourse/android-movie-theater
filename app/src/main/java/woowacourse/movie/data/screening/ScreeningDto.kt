package woowacourse.movie.data.screening

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import woowacourse.movie.data.movie.MovieDto
import woowacourse.movie.data.theater.TheaterDto

@Entity(
    tableName = "screenings",
    foreignKeys = [
        ForeignKey(
            entity = MovieDto::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = CASCADE,
        ),
        ForeignKey(
            entity = TheaterDto::class,
            parentColumns = ["id"],
            childColumns = ["theaterId"],
            onDelete = CASCADE,
        ),
    ],
)
data class ScreeningDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val movieId: Long,
    val theaterId: Long,
    val screeningTimestamp: Long,
)
