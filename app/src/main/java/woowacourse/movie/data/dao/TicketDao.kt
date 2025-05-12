package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import woowacourse.movie.data.entity.SeatEntity
import woowacourse.movie.data.entity.TicketEntity
import woowacourse.movie.data.entity.WholeTicketEntity

@Dao
interface TicketDao {
    @Insert
    fun save(ticketEntity: TicketEntity): Long

    @Insert
    fun save(seatEntities: List<SeatEntity>)

    @Transaction
    @Query(
        "SELECT * FROM ticket ",
    )
    fun findAll(): List<WholeTicketEntity>

    fun save(
        ticket: TicketEntity,
        seats: List<SeatEntity>,
    ) {
        val ticketId = save(ticket)
        save(seats.map { it.copy(ticketId = ticketId.toInt()) })
    }
}

/**
 * select * from seat
 * inner join ticket
 * on seat.id = ticket.seat_id
 * inner join cinema
 * on ticket.cinema_id = cinema.id
 */
