package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDate
import kotlin.time.Duration

@Entity(tableName = "movie")
data class Movie(
    @PrimaryKey var id: Long?,
    @ColumnInfo(name = "title") var title: String?,
    @ColumnInfo(name = "description") var description: String?,
    @ColumnInfo(name = "image_url") var imageUrl: String?,
    @ColumnInfo(name = "start_date") var startDate: LocalDate?,
    @ColumnInfo(name = "end_date") var endDate: LocalDate?,
    @ColumnInfo(name = "running_time") var runningTime: Duration?,
)
