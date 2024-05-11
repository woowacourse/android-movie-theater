package woowacourse.movie.domain.db.reservationdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.domain.model.Reservation
import woowacourse.movie.domain.model.Seat
import java.time.LocalDateTime

@Entity(tableName = "reservations")
data class ReservationEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "movie_name") val movieName: String,
    @ColumnInfo(name = "quantity") val ticketCount: Int,
    @ColumnInfo(name = "seats") val seats: List<Seat>,
    @ColumnInfo(name = "date_time") val dateTime: LocalDateTime,
    @ColumnInfo(name = "price") val totalPrice: Int,
) {
    fun toDomain() =
        Reservation(
            this.uid,
            this.theaterName,
            this.movieName,
            this.ticketCount,
            this.seats,
            this.dateTime,
        )

    companion object {
        fun Reservation.toEntity() =
            ReservationEntity(
                this.id,
                this.theaterName,
                this.movieTitle,
                this.ticketCount,
                this.seats,
                this.dateTime,
                this.totalPrice,
            )
    }
}
