package woowacourse.movie.model.data.theater

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "theaters")
class TheaterDto(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
)
