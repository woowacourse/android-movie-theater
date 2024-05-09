package woowacourse.movie.data.reservationref

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.CASCADE
import androidx.room.PrimaryKey
import woowacourse.movie.data.screeningref.ScreeningRefDto

@Entity(
    tableName = "reservationRefs",
    foreignKeys = [
        ForeignKey(
            ScreeningRefDto::class,
            parentColumns = ["id"],
            childColumns = ["screeningRefId"],
            onDelete = CASCADE,
        ),
    ],
)
data class ReservationRefDto(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val screeningRefId: Long,
    val seats: String,
)
