package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_theater")
class MovieTheater(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
)
