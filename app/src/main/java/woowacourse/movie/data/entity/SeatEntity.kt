package woowacourse.movie.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "movie_seats",
    foreignKeys = [
        ForeignKey(
            entity = ReservationEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservationId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["reservationId"])]
)
data class SeatEntity(
    val reservationId: Long,
    @ColumnInfo(name = "rowNumber")
    val row: Int,
    @ColumnInfo(name = "columnNumber")
    val col: Int,
    val grade: String,
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}