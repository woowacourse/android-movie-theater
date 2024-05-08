package woowacourse.movie.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Theater

@Entity(tableName = "reservationHistory")
data class ReservationHistory(
    @PrimaryKey
    val id: Int,
    @ColumnInfo(name = "reservation")
    val reservation: Reservation,
    @ColumnInfo(name = "theater")
    val theater: Theater,
)
