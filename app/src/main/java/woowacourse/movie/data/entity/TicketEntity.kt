package woowacourse.movie.data.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "tickets")
@Parcelize
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "date") val date: LocalDate,
    @ColumnInfo(name = "time") val time: LocalTime,
    @ColumnInfo(name = "head_count") val headCount: Int,
    @ColumnInfo(name = "seat") val seat: String,
    @ColumnInfo(name = "theater") val theater: String,
    @ColumnInfo(name = "price") val price: Int,
) : Parcelable
