package woowacourse.movie.data.dao

import androidx.room.*
import woowacourse.movie.data.entity.TicketBundleEntity
import woowacourse.movie.data.entity.TicketEntity

@Dao
interface ReservationDao {
    @Insert
    fun saveTicketBundle(bundle: TicketBundleEntity): Long

    @Insert
    fun saveTickets(tickets: List<TicketEntity>)

    @Transaction
    @Query("SELECT * FROM ticket_bundle")
    fun findAllTicketBundles(): List<TicketBundleDao>

    @Transaction
    @Query("SELECT * FROM ticket_bundle WHERE id = :bundleId")
    fun findTicketBundleWithTicketsById(bundleId: Int): TicketBundleDao?
}
