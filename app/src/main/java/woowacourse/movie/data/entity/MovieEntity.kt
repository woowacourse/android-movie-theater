package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.movie.ScreeningPeriod

@Entity(tableName = "movies")
data class MovieEntity(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    val title: String,
    val posterId: Int,
    val screeningPeriod: ScreeningPeriod,
    val runningTime: Int,
)
