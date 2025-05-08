package woowacourse.movie.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import woowacourse.movie.data.entity.TicketEntity

@Dao
interface TicketDao {
    @Insert
    fun save(ticketEntity: TicketEntity)

    @Query(
        "SELECT * FROM ticket ",
    )
    fun findAll(): List<TicketEntity>
}

/**
 * select * from seat
 * inner join ticket
 * on seat.id = ticket.seat_id
 * inner join cinema
 * on ticket.cinema_id = cinema.id
 */
