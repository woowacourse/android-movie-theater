package woowacourse.movie.model.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserTicketDao {
    @Query("SELECT * FROM user_tickets WHERE id=(:id)")
    fun find(id: Long): UserTicket

    @Query("SELECT * FROM user_tickets")
    fun findAll(): List<UserTicket>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(data: UserTicket)

    @Delete
    fun delete(userTicket: UserTicket)

    @Query("DELETE FROM user_tickets")
    fun deleteAll()
}
