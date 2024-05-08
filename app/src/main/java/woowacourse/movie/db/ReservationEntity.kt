package woowacourse.movie.db

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.Seat

@Parcelize
@Entity(tableName = "reservations")
data class ReservationEntity(
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screening_date") val screeningDate: String,
    @ColumnInfo(name = "screening_time") val screeningTime: String,
    @ColumnInfo(name = "selected_seats") val selectedSeats: List<Seat>,
    @ColumnInfo(name = "theater_name") val theaterName: String,
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var uid: Long = 0
}
