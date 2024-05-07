package woowacourse.movie.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.movie.ReservationDetail
import java.time.LocalDateTime

@Entity
data class UserTicket(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "theater") val theater: String,
    @ColumnInfo(name = "screening_start_date_time") val screeningStartDateTime: LocalDateTime,
    @ColumnInfo(name = "reservation_detail") val reservationDetail: ReservationDetail,
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)
