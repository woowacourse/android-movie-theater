package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tickets")
data class TicketEntity(
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "time") val time: String,
    @ColumnInfo(name = "head_count") val headCount: Int,
    @ColumnInfo(name = "seat") val seat: String,
    @ColumnInfo(name = "theater") val theater: String,
    @ColumnInfo(name = "price") val price: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
