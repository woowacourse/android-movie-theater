package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import woowacourse.movie.model.Seat
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tickets")
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "time") val time: LocalTime,
    @ColumnInfo(name = "head_count") val headCount: Int,
    @ColumnInfo(name = "seat") val seat: Set<Seat>,
    @ColumnInfo(name = "theater") val theater: String,
    @ColumnInfo(name = "price") val price: Int,
)
