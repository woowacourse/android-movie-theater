package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey val id: Long,
    val title: String,
    val description: String,
    val imageUrl: String,
    val startDate: LocalDate,
    val endDate: LocalDate,
)
