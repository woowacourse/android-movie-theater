package woowacourse.movie.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket")
data class Ticket(
    @PrimaryKey(autoGenerate = true) val id: Int?=null,
    @ColumnInfo(name="movieName") val movieName: String,
    @ColumnInfo(name="date") val date: String,
    @ColumnInfo(name="seatNumbers") val seatNumbers: String,
    @ColumnInfo(name="cinemaName") val cinemaName: String,
    @ColumnInfo(name="ticketPrice") val ticketPrice: Int,
)
