package woowacourse.movie.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.data.repository.HomeContentRepository.getMovieById
import woowacourse.movie.notification.MovieNotification

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

    fun toMovieNotification(): MovieNotification {
        val movieTitle = getMovieById(movieId)?.title ?: ""
        return MovieNotification(movieTitle, date, time)
    }
}
