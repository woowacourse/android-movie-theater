package woowacourse.movie.data.entity

import androidx.room.Embedded
import androidx.room.Relation

data class WholeTicketEntity(
    @Embedded
    val ticket: TicketEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "ticket_id",
    )
    val seats: List<SeatEntity>,
    @Relation(
        parentColumn = "cinema_id",
        entityColumn = "id",
    )
    val cinema: CinemaEntity,
)
