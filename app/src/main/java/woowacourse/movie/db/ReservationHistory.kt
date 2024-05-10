package woowacourse.movie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Reservation
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "reservationHistory")
data class ReservationHistory(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    @ColumnInfo(name = "reservation")
    val reservation: Reservation,
    @ColumnInfo(name = "theaterName")
    val theaterName: String,
    @ColumnInfo(name = "screeningDate")
    val screeningDate: LocalDate,
    @ColumnInfo(name = "screeningTime")
    val screeningTime: LocalTime,
)
