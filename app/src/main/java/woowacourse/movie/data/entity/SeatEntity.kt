package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ReservationInfoEntity::class,
            parentColumns = ["id"],
            childColumns = ["reservationId"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    tableName = "seats",
)
data class SeatEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val reservationId: Long,
    val row: Int,
    val column: Int,
)
