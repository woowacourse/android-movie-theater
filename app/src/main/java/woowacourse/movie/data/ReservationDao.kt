package woowacourse.movie.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDao {
    @Insert
    fun insertReservation(reservation: Reservation)

    @Delete
    fun deleteReservation(reservation: Reservation)

    @Query("SELECT * FROM reservationEntity WHERE uid = :uid")
    fun getTicketByUid(uid: Int): Reservation

    @Query("SELECT * FROM reservationEntity")
    fun getAll(): List<Reservation>
}
