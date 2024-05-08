package woowacourse.movie.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reservation_history")
data class ReservationHistoryEntity(
    val date: String,
    val time: String,
    val count: Int,
    val seats: String,
    val movieId: Long,
    val theaterPosition: Int,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L
}
