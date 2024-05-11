package woowacourse.movie.data.database.theater

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.data.database.theater.TheaterContract.TABLE_THEATER

@Entity(tableName = TABLE_THEATER)
data class TheaterEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val name: String,
    val screeningTimes: List<String>,
)
