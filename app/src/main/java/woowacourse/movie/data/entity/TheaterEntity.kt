package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theater")
data class TheaterEntity(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "name") val name: String,
)
