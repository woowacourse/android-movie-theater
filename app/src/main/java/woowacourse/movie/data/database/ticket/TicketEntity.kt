package woowacourse.movie.data.database.ticket

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = TicketContract.TABLE_TICKET)
data class TicketEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val title: String,
    val theater: String,
    val screeningStartDateTime: String,
    val reservationCount: Int,
    val seats: String,
    val price: Int,
)
