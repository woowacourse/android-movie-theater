package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "seat")
data class SeatEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "row") val row: Int,
    @ColumnInfo(name = "column") val column: Int,
    @ColumnInfo(name = "ticket_id") val ticketId: Int = 0,
)
