package woowacourse.movie.data.movie

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movies")
class MovieDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val description: String,
    val imageUrl: String,
    val startDate: String,
    val endDate: String,
    val runningTime: String,
)
