package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = MovieEntity::class,
            parentColumns = ["id"],
            childColumns = ["movieId"],
            onDelete = ForeignKey.RESTRICT,
        ),
        ForeignKey(
            entity = TheaterEntity::class,
            parentColumns = ["id"],
            childColumns = ["theaterId"],
            onDelete = ForeignKey.RESTRICT,
        ),
    ],
    tableName = "reservation",
)
data class ReservationInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val movieId: Long,
    val movieDateYear: Int,
    val movieDateMonth: Int,
    val movieDateDay: Int,
    val movieTimeHour: Int,
    val movieTimeMinute: Int,
    val ticketCount: Int,
    val theaterId: Long,
    val price: Int,
)
