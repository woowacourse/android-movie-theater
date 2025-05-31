package woowacourse.movie.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ticket_bundle")
data class TicketBundleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val dateTime: String,
    val theater: String,
)
