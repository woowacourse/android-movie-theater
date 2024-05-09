package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "reservation")
data class ReservationEntity(
    val cinemaName: String,
    val movieName: String,
    val synopsis: String,
    val runningTime: String,
    val releaseDate: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}
