package woowacourse.movie.ticket.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "ticket")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "movie_title") val movieTitle: String,
    @ColumnInfo(name = "screening_date") val screeningDate: String,
    @ColumnInfo(name = "screening_time") val screeningTime: String,
    @ColumnInfo(name = "seats_count") val seatsCount: Int,
    @ColumnInfo(name = "seats") val seats: String,
    @ColumnInfo(name = "theater_name") val theaterName: String,
    @ColumnInfo(name = "price") val price: Int,
) : Serializable
