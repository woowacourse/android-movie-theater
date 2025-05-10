package woowacourse.movie.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true) val ticketId: Long = 0L,
    @ColumnInfo(name = "movie_title")val title: String,
    @ColumnInfo(name = "movie_selected_date")val date: LocalDateTime,
    @ColumnInfo(name = "personnel")val personnel: Int,
    @ColumnInfo(name = "theater_name")val theaterName: String,
)
