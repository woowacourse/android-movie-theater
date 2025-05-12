package woowacourse.movie.model.reservation

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import woowacourse.movie.model.seat.Seats
import java.time.LocalDate

@Entity(tableName = "reservationsInfo")
@Parcelize
data class ReservationInfo(
    val title: String,
    val date: LocalDate,
    val time: String,
    val seats: Seats,
    val price: Int,
    val theaterName: String,
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
) : Parcelable
